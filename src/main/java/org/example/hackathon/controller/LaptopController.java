package org.example.hackathon.controller;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.example.hackathon.model.Laptop;
import org.example.hackathon.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/laptops")
public class LaptopController {

    private final LaptopService laptopService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Laptop> laptops;
        if (keyword != null && !keyword.trim().isEmpty()) {
            laptops = laptopService.searchLaptops(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            laptops = laptopService.getAllLaptops();
        }
        model.addAttribute("laptops", laptops);
        return "laptop-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("laptop", new Laptop());
        return "laptop-form";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("laptop") Laptop laptop,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        if (bindingResult.hasErrors()) {
            return "laptop-form";
        }
        laptop.setImage(handleImageUpload(imageFile));
        laptopService.addLaptop(laptop);
        return "redirect:/laptops";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Laptop> opt = laptopService.getLaptopById(id);
        if (opt.isEmpty()) return "redirect:/laptops";
        model.addAttribute("laptop", opt.get());
        model.addAttribute("isEdit", true);
        return "laptop-form";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("laptop") Laptop laptop,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "existingImage", required = false) String existingImage,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "laptop-form";
        }

        laptop.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            laptop.setImage(handleImageUpload(imageFile));
        } else {
            laptop.setImage(existingImage != null ? existingImage : "default.png");
        }

        laptopService.updateLaptop(laptop);
        return "redirect:/laptops";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        laptopService.deleteLaptop(id);
        return "redirect:/laptops";
    }

    private String handleImageUpload(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) return "default.png";

        String original = imageFile.getOriginalFilename();
        if (original == null) return "default.png";

        String ext = original.substring(original.lastIndexOf(".")).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".jpeg") && !ext.equals(".png"))
            return "default.png";

        String newFileName = UUID.randomUUID() + ext;
        try {
            String uploadDir = servletContext.getRealPath("/uploads");
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) Files.createDirectories(path);
            imageFile.transferTo(new File(uploadDir + File.separator + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "default.png";
        }
        return newFileName;
    }
}