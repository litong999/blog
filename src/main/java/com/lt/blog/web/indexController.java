package com.lt.blog.web;


import com.lt.blog.service.BlogService;
import com.lt.blog.service.CommentService;
import com.lt.blog.service.TagService;
import com.lt.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(15));
        model.addAttribute("tags", tagService.listTagTop(100));
//        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        model.addAttribute("hotBlogs", blogService.listViewsTop(8));

        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 3, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page",blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query",query);
        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }


    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
