package controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Hero;
import service.HeroService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/api/heroes")

public class HeroController {
    private final
    HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public void findHero(@PathVariable("id") Integer id, HttpServletResponse response) {
        Hero hero = heroService.findHero(id);
        System.out.println(hero.getName());
        String json = "{\"id\":\"" + hero.getId() + "\",\"name\":\"" + hero.getName() + "\"}";
        try {
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = {RequestMethod.GET})
    public void findAllHero(HttpServletResponse response) {
        List<Hero> heroes = heroService.findAllHeroes();
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (Hero heroe : heroes) {

            json.append("{\"id\":\"").append(heroe.getId()).append("\",\"name\":\"").append(heroe.getName()).append("\"}");
            json.append(",");
        }
        json.deleteCharAt(json.lastIndexOf(","));
        json.append("]");
        try {
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public void deleteHero(@PathVariable("id") Integer id, HttpServletResponse response) {
        if (heroService.deleteHero(id)) {
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);


            try {
                response.getWriter().write("1");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    @RequestMapping(value = "", method = {RequestMethod.PUT})
    public void updateHero(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Hero hero = objectMapper.readValue(request.getReader(), Hero.class);
        System.out.println(hero.getId() + ".." + hero.getName());
        if (heroService.updateHero(hero)) {
            try {
                response.getWriter().write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @RequestMapping(value = "", method = {RequestMethod.POST})
    public void insertHero(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Hero hero = objectMapper.readValue(request.getReader(), Hero.class);
        System.out.println("++++++++++++++++++++++" + heroService.insertHero(hero));
        hero = heroService.findHeroByName(hero.getName());
        try {
            response.getWriter().write("{\"id\":\"" + hero.getId() + "\",\"name\":\"" + hero.getName() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/name", method = {RequestMethod.GET})
    public void searchHero(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf8");
        String term = request.getParameter("name");
        term = "%"+term+"%";
        System.out.println("------"+term+"---------");
        List<Hero> heroes = heroService.searchHero(term);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(heroes);
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
