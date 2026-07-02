package com.example.carstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.carstore.entity.Car;
import com.example.carstore.service.CarService;

@Controller
@RequestMapping("/admin")
public class AdminCarController {

    private final CarService carService;

    public AdminCarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String listCars(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin-cars";
    }

    @GetMapping("/create-car")
    public String createCar(Model model) {
        model.addAttribute("car", new Car());
        return "admin-create-car";
    }

    @GetMapping("/cars/edit/{id}")
    public String editCar(@PathVariable Integer id, Model model) {
        model.addAttribute("car", carService.findById(id));
        return "admin-create-car";
    }

    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable Integer id) {
        carService.delete(id);
        return "redirect:/admin/cars";
    }
}
