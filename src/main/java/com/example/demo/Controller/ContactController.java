
package com.example.demo.Controller;

import com.example.demo.Enities.Contact;
import com.example.demo.Enities.User;
import com.example.demo.Forms.ContactForm;
import com.example.demo.helper.AppConstants;
import com.example.demo.helper.Helper;
import com.example.demo.helper.Message;
import com.example.demo.helper.MessageType;
import com.example.demo.services.ContactService;
import com.example.demo.services.ImageSevice;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageSevice imageService;

    @Autowired
    private UserService userService;

    // GET Method - Show form
    @RequestMapping("/add")
    public String addContactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "user/add_contact";
    }

    // POST Method - Handle form submission
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(
            @Valid @ModelAttribute("contactForm") ContactForm contactForm,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Please correct the form errors.")
                            .type(MessageType.red)
                            .build());
            return "redirect:/user/contacts/add";
        }

        try {
            String email = Helper.getEmailOfLoggedInUser(authentication);
            User user = userService.getUserByEmail(email);

            String fileURL = imageService.uploadImage(contactForm.getPicture());

            Contact contact = Contact.builder()
                    .name(contactForm.getName())
                    .email(contactForm.getEmail())
                    .phoneNumber(contactForm.getPhoneNumber())
                    .about(contactForm.getAbout())
                    .address(contactForm.getAddress())
                    .linkedInLink(contactForm.getLinkedInLink())
                    .webSiteLink(contactForm.getWebSiteLink())
                    .picture(fileURL)
                    .favorite(contactForm.isFavorite())
                    .user(user)
                    .build();

            contactService.save(contact);

            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Your contact was successfully added!")
                            .type(MessageType.green)
                            .build());

            return "redirect:/user/contacts/add";

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Something went wrong!")
                            .type(MessageType.red)
                            .build());
            return "redirect:/user/contacts/add";
        }
    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ascending") String direction,
            Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        // contactService.getByUser(user,page , size,sortBy,direction);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        // load all the user contacts
        model.addAttribute("pageContact", pageContact);
        return "user/contacts";
    }

    // serach handlear

    @GetMapping("/search")
    public String searchHandlear(
            @RequestParam("field") String field,
            @RequestParam("keyword") String value,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order, Model model,
            Authentication authentication

    ) {
        System.out.println("Searching: " + field + " with " + value);
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (field.equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(value, size, page, sortBy, order, user);

        } else if (field.equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(value, size, page, sortBy, order, user);

        } else if (field.equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(value, size, page, sortBy, order, user);

        }
        System.out.println(pageContact);
        model.addAttribute("pageContact", pageContact);

        return "user/search";
    }

    // delete contact

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId) {
        contactService.delete(contactId);
        return "redirect:/user/contacts";
    }

    // Show Update Contact Form
    @GetMapping("/update/{contactId}")
    public String showUpdateForm(@PathVariable("contactId") String contactId,
            Model model,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
            Contact contact = contactService.getByIdAndUser(contactId, user);

            ContactForm contactForm = ContactForm.builder()
                    .name(contact.getName())
                    .email(contact.getEmail())
                    .phoneNumber(contact.getPhoneNumber())
                    .about(contact.getAbout())
                    .address(contact.getAddress())
                    .linkedInLink(contact.getLinkedInLink())
                    .webSiteLink(contact.getWebSiteLink())
                    .favorite(contact.isFavorite())
                    .pictureUrl(contact.getPicture()) // Add this
                    .build();

            model.addAttribute("contactId", contactId);
            model.addAttribute("contactForm", contactForm);
            return "user/update_contact";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Invalid contact or access denied.")
                            .type(MessageType.red)
                            .build());
            return "redirect:/user/contacts";
        }
    }

    // Handle Update Submission
    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute("contactForm") ContactForm contactForm,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Please correct the errors.")
                            .type(MessageType.red)
                            .build());
            return "redirect:/user/contacts/update/" + contactId;
        }

        try {
            User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
            Contact contact = contactService.getByIdAndUser(contactId, user);

            // Only update the image if a new one is uploaded
            String fileURL = contact.getPicture();
            if (!contactForm.getPicture().isEmpty()) {
                fileURL = imageService.uploadImage(contactForm.getPicture());
            }

            contact.setName(contactForm.getName());
            contact.setEmail(contactForm.getEmail());
            contact.setPhoneNumber(contactForm.getPhoneNumber());
            contact.setAbout(contactForm.getAbout());
            contact.setAddress(contactForm.getAddress());
            contact.setLinkedInLink(contactForm.getLinkedInLink());
            contact.setWebSiteLink(contactForm.getWebSiteLink());
            contact.setFavorite(contactForm.isFavorite());
            contact.setPicture(fileURL);
            contactService.update(contact, user);

           // contactService.update(contact);

            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Contact updated successfully.")
                            .type(MessageType.green)
                            .build());

            return "redirect:/user/contacts";

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message",
                    Message.builder()
                            .content("Something went wrong during update.")
                            .type(MessageType.red)
                            .build());
            return "redirect:/user/contacts/update/" + contactId;
        }
    }

}
