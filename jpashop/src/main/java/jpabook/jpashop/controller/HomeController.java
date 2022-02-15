package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {

//    Logger log = LoggerFactory.getLogger(HomeController.class); // 현재 코드와 동일하게 @Slf4j 애노테이션을 사용하면 된다. lombok

    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }
}
