package com.project.railway.controller;

import com.project.railway.data.entity.SearchData;
import com.project.railway.data.entity.Seat;
import com.project.railway.data.entity.Station;
import com.project.railway.data.repository.SeatRepository;
import com.project.railway.data.repository.StationRepository;
import com.project.railway.data.repository.TrainRepository;
import com.project.railway.service.EmailService;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    EmailService emailService;

    @GetMapping("/")
    public String home(Model model){
        SearchData search = new SearchData(getStations());
        model.addAttribute("searchData", search);
        return  "search";
    }

    @GetMapping("/search")
    public String searchResult(Model model){
        SearchData search = new SearchData(getStations());

        model.addAttribute("searchData", search);
        return "search";
    }

    @GetMapping("/searchresults")
    public String getSearchResultPage(Model model){
        SearchData search = new SearchData(getStations());
        model.addAttribute("searchData", search);
        return "search";
    }

    @GetMapping("/contacts")
    public String getContactsPage(Model model,
                                  @ModelAttribute(name = "name") String clientName,
                                  @ModelAttribute(name = "phoneNumber") String phoneNumber,
                                  @ModelAttribute(name = "email") String email,
                                  @ModelAttribute(name = "message") String message){



        return "contacts";
    }

    @PostMapping("/contacts/send-message")
    public String postSendMessage(Model model,
                                  @ModelAttribute(name = "name") String clientName,
                                  @ModelAttribute(name = "phoneNumber") String phoneNumber,
                                  @ModelAttribute(name = "email") String email,
                                  @ModelAttribute(name = "message") String message) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo("407c263f6c5f75");
        mailMessage.setFrom("407c263f6c5f75");

        mailMessage.setSubject("БДЖ Резервация онлайн - клиент изпрати съобщение!");
        mailMessage.setText("Клиент: " + clientName
                + "\nТелефонен номер: " + phoneNumber
                + "\nE-mail: " + email
                + "\nСъобщение: " + message);

        emailService.sendEmail(mailMessage);
        return "redirect:/contacts";
    }

    @PostMapping("/search")
    public String getSearchResults(Model model){
        return "searchresults";
    }

    public List<Station> getStations(){
        List<Station> stations = stationRepository.findAllStations();
        Collections.sort(stations);
        return stations;
    }
}
