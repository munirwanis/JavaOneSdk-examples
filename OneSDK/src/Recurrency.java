
import Client.GatewayServiceClient;
import DataContracts.CreditCardTransaction.CreditCard;
import DataContracts.CreditCardTransaction.CreditCardTransaction;
import DataContracts.Order.Order;
import DataContracts.Sale.CreateSaleRequest;
import DataContracts.Sale.CreateSaleResponse;
import EnumTypes.CreditCardBrandEnum;
import EnumTypes.FrequencyEnum;
import Utility.HttpResponseGenerics;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author munir
 */
public class Recurrency {
    public static String getResponse() {
        try {
            // Define loja
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria um cartão de crédito e define endereço de cobrança
            CreditCard creditCard = new CreditCard();
            creditCard.setCreditCardBrand(CreditCardBrandEnum.Visa);
            creditCard.setCreditCardNumber("4111111111111111");
            creditCard.setExpMonth(10);
            creditCard.setExpYear(22);
            creditCard.setHolderName("LUKE SKYWALKER");
            creditCard.setSecurityCode("123");

            // Cria a transação de cartão de crédito e define cartão criado anteriormente
            CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
            creditCardTransaction.setAmountInCents(10000L);
            creditCardTransaction.setCreditCard(creditCard);
            creditCardTransaction.setInstallmentCount(1);
            creditCardTransaction.setRecurrency(new DataContracts.Recurrency.Recurrency());
            creditCardTransaction.getRecurrency().setDateToStartBilling(Date.valueOf("2015-12-14"));
            creditCardTransaction.getRecurrency().setFrequency(FrequencyEnum.Monthly);
            creditCardTransaction.getRecurrency().setInterval(1);
            creditCardTransaction.getRecurrency().setRecurrences(5);
           
            // Cria o objeto order para adicionar o Order Reference
            Order order = new Order();
            order.setOrderReference("NúmeroDoPedido");

            // Cria o Sale Request para enviar o objeto de request
            CreateSaleRequest createSaleRequest = new CreateSaleRequest();
            createSaleRequest.setCreditCardTransactionCollection(new ArrayList<>());
            createSaleRequest.getCreditCardTransactionCollection().add(creditCardTransaction);
            createSaleRequest.setOrder(order);

            // Cria o cliente que vai enviar a transação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Submete a transação e retorna a resposta do gateway
            HttpResponseGenerics<CreateSaleResponse, CreateSaleRequest> httpResponse
                    = serviceClient.getSale().Create(createSaleRequest);
            
            return httpResponse.getRawResponse();

        } catch (Exception ex) {
            return "Error";
        }

    }
}
