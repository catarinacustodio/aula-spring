package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.BankDto;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks")
public class BankController {
	private static Logger logger = LoggerFactory.getLogger(BankController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String bankForm(Model model) {
		logger.info("bankForm");
		model.addAttribute("bank", new BankDto());
		model.addAttribute("banks", Bank.banks);
		return "banksView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String bankSubmit(Model model, @ModelAttribute BankDto bankDto) {
		logger.info("bankSubmit name:{}, code:{}", bankDto.getName(), bankDto.getCode());

		try {
			new Bank(bankDto.getName(), bankDto.getCode());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the bank");
			model.addAttribute("bank", bankDto);
			model.addAttribute("banks", Bank.banks);
			return "banksView";
		}

		return "redirect:/banks";
	}

	@RequestMapping(value = "/bank/{code}", method = RequestMethod.GET)
	public String showBank(Model model, @PathVariable String code) {
		logger.info("showBank code:{}", code);

		Bank bank = Bank.getBankByCode(code);

		new Client(bank, "ID01", "Zé", 22);
		new Client(bank, "ID02", "Manel", 44);

		model.addAttribute("bank", bank);
		model.addAttribute("client", new ClientDto());
		model.addAttribute("clients", bank.getClients());
		return "bankView";
	}
}
