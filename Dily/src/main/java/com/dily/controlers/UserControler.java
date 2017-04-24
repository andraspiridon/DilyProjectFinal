package com.dily.controlers;

import com.dily.Mapper.UserMapper;
import com.dily.entities.User;
import com.dily.repositories.UserRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Andra on 4/11/2017.
 */
@Controller
@RequestMapping("/users")
public class UserControler {

    @Autowired
    private UserRepository repository;

    private DataSource dataSource;
    JdbcTemplate jdbcTemplate =new JdbcTemplate();
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/{page}/allUsers", method = RequestMethod.GET)
    public String findAll(@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by user_id) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "users";
    }


   /* @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String findAllUsers(Model model) {
       // List<User> list ;
       // UserMapper userMapper=new UserMapper();
       // list = this.jdbcTemplate.query( "select * from user_table where name like '%Rusu%'", userMapper);
        model.addAttribute("users",repository.findAll());
        return "users";
    }
    */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete (@PathVariable int id){
        repository.delete(id);
        return new ModelAndView ("redirect:/users/1/allUsers");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newUser() {
        return "new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("user_id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               @RequestParam("dateOfBirth") String dateOfBirth) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());


        repository.save(new User(id,name,username,password,email,sqlDate));
        return new ModelAndView("redirect:/users/1/allUsers");
    }

    @RequestMapping(value = "/allRequested", method = RequestMethod.POST)
    public String eroare (){
        return "requested";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("user_id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               @RequestParam("dateOfBirth") String dateOfBirth
                               ) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        User user= repository.findOne(id);
        //user.setUser_id(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDateOfBirth(sqlDate);
        repository.save(user);
        return new ModelAndView("redirect:/users/1/allUsers");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable int id,
                       Model model) {
        User user = repository.findOne(id);
        model.addAttribute("user", user);
        return "edit";
    }
    @RequestMapping(value="/{page}/descSortId", method = RequestMethod.GET)
    public String descSortId (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by user_id desc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortDescId";
    }

    @RequestMapping(value="/{page}/ascSortId", method = RequestMethod.GET)
    public String ascSortId (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by user_id asc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortAscId";
    }

    @RequestMapping(value="/{page}/ascSortName", method = RequestMethod.GET)
    public String ascSortName (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by name asc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortAscName";
    }

    @RequestMapping(value="/{page}/descSortName", method = RequestMethod.GET)
    public String descSortName (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by name desc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortDescName";
    }

    @RequestMapping(value="/{page}/descSortDate", method = RequestMethod.GET)
    public String descSortDate (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by date_of_birth desc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortDescDate";
    }

    @RequestMapping(value="/{page}/ascSortDate", method = RequestMethod.GET)
    public String ascSortDate (@PathVariable int page, Model model) {
        UserMapper userMapper=new UserMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  user_table order by date_of_birth asc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return "sortAscDate";
    }

    @RequestMapping(value="/filterName", method = RequestMethod.GET)
    public String filterName() {
        return "filterName";
    }

    @RequestMapping(value="/filterByName", method = RequestMethod.POST)
    public String filterByName (@RequestParam("filter_name") String htmlName,
                                 Model model) {

        UserMapper userMapper=new UserMapper();
        String var = "'"+"%"+htmlName+"%"+"'";
        String sql="SELECT  * FROM  user_table where name like" + var;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return ("filterBy");
    }

    @RequestMapping(value="/filterId", method = RequestMethod.GET)
    public String filterId() {
        return "filterId";
    }

    @RequestMapping(value="/filterById", method = RequestMethod.POST)
    public String filterById (@RequestParam("filter_id") int htmlId,
                                 Model model) {

        UserMapper userMapper=new UserMapper();
        String sql="SELECT  * FROM  user_table where user_id =" + htmlId;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return ("filterBy");
    }

    /*
    @RequestMapping(value="/filterDate", method = RequestMethod.GET)
    public String filterDate() {
        return "filterDate";
    }

    @RequestMapping(value="/filterByDate", method = RequestMethod.POST)
    public String filterByDate (@RequestParam("filter_date") String dateOfBirth,
                               Model model) {

            DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        UserMapper userMapper=new UserMapper();
        String sql="SELECT  * FROM  user_table where date_of_birth =" + sqlDate;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return ("filterBy");
    }
    */

    @RequestMapping(value="/birthdayToday", method = RequestMethod.GET)
    public String birthdayToday (Model model) {

        UserMapper userMapper=new UserMapper();
        String sql="SELECT  * FROM  user_table where to_char(date_of_birth,'DD-MM')=to_char(sysdate,'DD-MM')";
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return ("filterBy");
    }

    @RequestMapping(value="/relatives", method = RequestMethod.GET)
    public String relatives() {
        return "findRelatives";
    }

    @RequestMapping(value="/findRelatives", method = RequestMethod.POST)
    public String findRelatives (@RequestParam("filter_name") String htmlName,
                                Model model) {

        UserMapper userMapper=new UserMapper();
        String var = "'"+"%"+htmlName+"%"+"'";
        String sql="SELECT  * FROM  user_table where name like " + var;
        model.addAttribute("users",this.jdbcTemplate.query( sql, userMapper));
        return ("filterBy");
    }
}