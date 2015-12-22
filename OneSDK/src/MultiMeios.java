
import Client.GatewayServiceClient;
import DataContracts.CreditCardTransaction.CreditCard;
import DataContracts.CreditCardTransaction.CreditCardTransaction;
import DataContracts.Order.Order;
import DataContracts.Sale.CreateSaleRequest;
import DataContracts.Sale.CreateSaleResponse;
import EnumTypes.CreditCardBrandEnum;
import Utility.HttpResponseGenerics;
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
public class MultiMeios {

    public static String getResponse() {
        try {
            // Define loja
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria a transação de cartão de crédito
            CreditCardTransaction creditCardTransaction1 = new CreditCardTransaction();
            creditCardTransaction1.setAmountInCents(10000L);
            creditCardTransaction1.setCreditCard(new CreditCard());
            creditCardTransaction1.getCreditCard().setCreditCardBrand(CreditCardBrandEnum.Visa);
            creditCardTransaction1.getCreditCard().setCreditCardNumber("4111111111111111");
            creditCardTransaction1.getCreditCard().setExpMonth(10);
            creditCardTransaction1.getCreditCard().setExpYear(22);
            creditCardTransaction1.getCreditCard().setHolderName("LUKE SKYWALKER");
            creditCardTransaction1.getCreditCard().setSecurityCode("123");
            creditCardTransaction1.setInstallmentCount(1);
            
            // Cria a transação de cartão de crédito
            CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction();
            creditCardTransaction2.setAmountInCents(5000L);
            creditCardTransaction2.setCreditCard(new CreditCard());
            creditCardTransaction2.getCreditCard().setCreditCardBrand(CreditCardBrandEnum.Mastercard);
            creditCardTransaction2.getCreditCard().setCreditCardNumber("5444444444444444");
            creditCardTransaction2.getCreditCard().setExpMonth(9);
            creditCardTransaction2.getCreditCard().setExpYear(23);
            creditCardTransaction2.getCreditCard().setHolderName("LORD VADER");
            creditCardTransaction2.getCreditCard().setSecurityCode("321");
            creditCardTransaction2.setInstallmentCount(1);

            // Cria o objeto order para adicionar o Order Reference
            Order order = new Order();
            order.setOrderReference("NúmeroDoPedido");

            // Cria o Sale Request para enviar o objeto de request
            CreateSaleRequest createSaleRequest = new CreateSaleRequest();
            createSaleRequest.setCreditCardTransactionCollection(new ArrayList<>());
            createSaleRequest.getCreditCardTransactionCollection().add(creditCardTransaction1);
            createSaleRequest.getCreditCardTransactionCollection().add(creditCardTransaction2);
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
