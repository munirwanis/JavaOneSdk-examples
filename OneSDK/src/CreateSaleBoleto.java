
import Client.GatewayServiceClient;
import DataContracts.BoletoTransaction.BoletoTransaction;
import DataContracts.BoletoTransaction.BoletoTransactionOptions;
import DataContracts.Order.Order;
import DataContracts.Sale.CreateSaleRequest;
import DataContracts.Sale.CreateSaleResponse;
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
public class CreateSaleBoleto {
    
    public static String getResponse() {
        try {
            // Define loja
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria um objeto de transação de boleto
            BoletoTransaction boletoTransaction = new BoletoTransaction();
            boletoTransaction.setAmountInCents(10000L);
            boletoTransaction.setBankNumber("237");
            boletoTransaction.setDocumentNumber("12345678901");
            boletoTransaction.setInstructions("Pagar antes do vencimento");
            
            // Cria o objeto de options
            BoletoTransactionOptions boletoTransactionOptions = new BoletoTransactionOptions();
            boletoTransactionOptions.setDaysToAddInBoletoExpirationDate(5);
            boletoTransaction.setOptions(boletoTransactionOptions);
            
            // Cria o objeto order para adicionar o Order Reference
            Order order = new Order();
            order.setOrderReference("NúmeroDoPedido");

            // Cria o Sale Request para enviar o objeto de request
            CreateSaleRequest createSaleRequest = new CreateSaleRequest();
            createSaleRequest.setBoletoTransactionCollection(new ArrayList<>());
            createSaleRequest.getBoletoTransactionCollection().add(boletoTransaction);
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
