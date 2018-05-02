package pt.ulisboa.tecnico.softeng.bank.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Bank {
	public static Set<Bank> banks = new HashSet<Bank>();

	private String name;
	private String code;

	private Set<Client> clients = new HashSet<Client>();

	public Bank() {
	}

	public Bank(String name, String code) {
		checkName(name);
		this.name = name;

		checkCode(code);
		this.code = code;

		banks.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().equals("")) {
			throw new BankException();
		}

		for (Bank bank : banks) {
			if (bank.getCode().equals(code)) {
				throw new BankException();
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().equals("")) {
			throw new BankException();
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void addClient(Client client) {
		this.clients.add(client);

	}

	public Set<Client> getClients() {
		return this.clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public Client getClientById(String id) {
		for (Client client : clients) {
			if (client.getId().equals(id)) {
				return client;
			}
		}
		return null;
	}

	public static Bank getBankByCode(String code) {
		for (Bank bank : banks) {
			if (bank.getCode().equals(code)) {
				return bank;
			}
		}
		return null;
	}

}
