package com.example.federation;

import com.example.federation.domain.News;
import com.example.federation.repos.NewsRepo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class FederationApplication {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private NewsRepo newsRepo;

    //Скачивание календаря
    @GetMapping("/downloadpdf")
    public void  downloadPdf (HttpServletResponse response ) throws IOException{
        File file= new File("files\\cal.pdf");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename =" + file.getName();
        response.setHeader(headerKey,headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[8192];
        int bytesRead  = -1;
        while ((bytesRead = inputStream.read(buffer))!= -1){
            outputStream.write(buffer,0,bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }



    @GetMapping("/calendar")
    public String  calendar (){
        return "calendar";
    }

    @GetMapping("/about")
    public String  about (){
        return "about";
    }
    @GetMapping("/main")
    public String  main (){
        return "main";
    }
    @GetMapping("/")
    public String  index (){
        return "main";
    }


//    @GetMapping("/greeting")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }


    //Новости для пользователя
    @GetMapping("/actual")
    public String actual (Model model ){
        Iterable<News> news = newsRepo.findAll();
        model.addAttribute("news",news);
        return "actual";
    }

    @GetMapping("/actual/{id}")
    public String onePage(@PathVariable("id") long id,
                          //@PathVariable(value = "name", required =false) String name,
                          Model model){
        News news = newsRepo.findById(id);
        model.addAttribute("news",news);
        return "OnePage";
    }

    //Админка
    @GetMapping("/news")
    public String news (Model model ){
        Iterable<News> news = newsRepo.findAll();
        model.addAttribute("news",news);
        return "news";
    }

    @PostMapping("/news")
    public String addNews (@RequestParam String name, @RequestParam String info, Map<String ,Object> model,@RequestParam("file") MultipartFile file ) throws IOException {
        News news = new News(name,info);
        if (file!= null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo (new File(uploadPath + "/" + resultFilename));

            news.setFilename(resultFilename);
        }
        newsRepo.save(news);

        Iterable<News> neds = newsRepo.findAll();
        model.put("news",neds);
        return "news";
    }

    @PostMapping("/news/delete/{id}")
    public String deleter (@PathVariable("id") long id, Model model ){
        //News news = (News) newsRepo.findById(id);
        News news = newsRepo.findById(id);
        newsRepo.delete( news);
        return "redirect:/news";
    }














//Просто так, не нужен (скачивание просто пробное)
//    @RequestMapping(method = RequestMethod.GET, value = "/download")
//    public ModelAndView download (){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("../static/index");
//        return modelAndView;
//    }

}