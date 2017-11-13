package com.itcast.client;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.itcast.crm.domain.Customer;

public class Client {
	public static void main(String[] args) {
		Collection<? extends Customer> collection = WebClient.create("http://localhost:9002/crm_management/services/customerService/noConnectCustomer").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		System.out.println(collection);
	}
}
