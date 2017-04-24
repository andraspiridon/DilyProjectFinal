package com.dily.controlers;

import com.dily.Mapper.TagMapper;
import com.dily.Mapper.UserMapper;
import com.dily.entities.Tag;
import com.dily.entities.User;
import com.dily.repositories.TagRepository;
import com.dily.repositories.UserRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rusum on 13.04.2017.
 */
@Controller
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagRepository repository;

    private DataSource dataSource;
    JdbcTemplate jdbcTemplate =new JdbcTemplate();
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/{page}/allTags", method = RequestMethod.GET)
    public String findAll(@PathVariable int page, Model model) {
        TagMapper tagMapper=new TagMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  tag order by tag_id) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return "tags";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete (@PathVariable int id){
        repository.delete(id);
        return new ModelAndView ("redirect:/tags/1/allTags");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newUser() {
        return "newTag";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("tag_id") int id,
                               @RequestParam("tag_name") String name
                               ) {


        repository.save(new Tag(id,name));
        return new ModelAndView("redirect:/tags/1/allTags");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable int id,
                       Model model) {
        Tag tag = repository.findOne(id);
        model.addAttribute("tag", tag);
        return "editTag";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("tag_id") int id,
                               @RequestParam("tag_name") String name
    ) {

        Tag tag= repository.findOne(id);
       // tag.setTag_id(id);
        tag.setTag_name(name);
        repository.save(tag);
        return new ModelAndView("redirect:/tags/1/allTags");
    }


    @RequestMapping(value="/{page}/descSortId", method = RequestMethod.GET)
    public String descSortId (@PathVariable int page, Model model) {
        TagMapper tagMapper=new TagMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  tag order by tag_id desc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return "sortDescIdTag";
    }


    @RequestMapping(value="/{page}/ascSortId", method = RequestMethod.GET)
    public String ascSortId (@PathVariable int page, Model model) {
        TagMapper tagMapper=new TagMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  tag order by tag_id asc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return "sortAscIdTag";
    }

    @RequestMapping(value="/{page}/ascSortName", method = RequestMethod.GET)
    public String ascSortName (@PathVariable int page, Model model) {
        TagMapper tagMapper=new TagMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  tag order by tag_name asc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return "sortAscNameTag";
    }


    @RequestMapping(value="/{page}/descSortName", method = RequestMethod.GET)
    public String descSortName (@PathVariable int page, Model model) {
        TagMapper tagMapper=new TagMapper();
        int pageLower,pageUpper;
        pageLower=(page-1)*30+1;
        pageUpper=(page+1)*30;
        String sql="SELECT * FROM ( SELECT  q.*, rownum rn FROM ( SELECT  * FROM  tag order by tag_name desc) q) WHERE   rn BETWEEN "+ pageLower/2 +" AND "+ pageUpper/2;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return "sortDescNameTag";
    }




    @RequestMapping(value="/filterName", method = RequestMethod.GET)
    public String filterName() {
        return "tagFilterName";
    }

    @RequestMapping(value="/filterByName", method = RequestMethod.POST)
    public String filterByName (@RequestParam("filter_name") String htmlName,
                                Model model) {

        TagMapper tagMapper=new TagMapper();
        String var = "'"+"%"+htmlName+"%"+"'";
        String sql="SELECT  * FROM  tag where tag_name like" + var;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return ("tagFilterBy");
    }


    @RequestMapping(value="/filterId", method = RequestMethod.GET)
    public String filterId() {
        return "tagFilterId";
    }

    @RequestMapping(value="/filterById", method = RequestMethod.POST)
    public String filterById (@RequestParam("filter_id") int htmlId,
                              Model model) {

        TagMapper tagMapper=new TagMapper();
        String sql="SELECT  * FROM  tag where tag_id =" + htmlId;
        model.addAttribute("tags",this.jdbcTemplate.query( sql, tagMapper));
        return ("tagfilterBy");
    }

}
