package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String clientForm(Model model, @PathVariable String code) {
        logger.info("clientForm");
        model.addAttribute("bank", Bank.getBankByCode(code));
        model.addAttribute("client", new ClientDto());
        return "bankView";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String clientSubmit(Model model, @ModelAttribute ClientDto clientDto, @PathVariable String code) {
        logger.info("clientSubmit name:{}, age:{}", clientDto.getName(), clientDto.getAge());

        Bank bank = Bank.getBankByCode(code);

        try {
            new Client(bank,
                    bank.getCode() + Integer.toString((int) (Math.random() * 50 + 1)),
                    clientDto.getName(),
                    clientDto.getAge());

        } catch (BankException be) {
            model.addAttribute("error", "Error: it was not possible to create the client");
            model.addAttribute("client", clientDto);
            model.addAttribute("clients", Bank.getBankByCode(clientDto.getBank()).getClients());
            return "bankView";
        }

        model.addAttribute("bank", bank);
        model.addAttribute("client", clientDto);

        return "redirect:/banks/bank/" + bank.getCode() + "/clients";
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public String showClient(Model model, @PathVariable String code, @PathVariable String id) {
        logger.info("showClient id:{}", id);

        Bank bank = Bank.getBankByCode(code);
        Client client = bank.getClientById(id);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setAge(client.getAge());
        clientDto.setBank(bank.getCode());

        model.addAttribute("bank", bank);
        model.addAttribute("client", clientDto);

        return "clientView";
    }

}
