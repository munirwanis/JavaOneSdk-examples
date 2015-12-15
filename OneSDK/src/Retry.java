
import Client.GatewayServiceClient;
import DataContracts.CreditCardTransaction.RetrySaleCreditCardTransaction;
import DataContracts.Sale.RetrySaleRequest;
import DataContracts.Sale.RetrySaleResponse;
import Utility.HttpResponseGenerics;
import java.util.ArrayList;
import java.util.List;
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
public class Retry {

    public static String getResponseWithOrderKey() {
        try {
            // Define loja 
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria o cliente que vai efetuar a operação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Define a chave do pedido
            UUID orderKey = UUID.fromString("219d7581-78e2-4aa9-b708-b7c585780bfc"); // Chave do pedido

            // Submete a requisição
            HttpResponseGenerics<RetrySaleResponse, RetrySaleRequest> httpResponse
                    = serviceClient.getSale().Retry(orderKey);

            return httpResponse.getRawResponse();
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String getResponseWithExtras() {
        try {
            // Define loja 
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria o cliente que vai efetuar a operação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            RetrySaleRequest retrySaleRequest = new RetrySaleRequest();

            // Define os detalhes da transação de cartão de crédito
            RetrySaleCreditCardTransaction retrySaleCreditCardTransaction = new RetrySaleCreditCardTransaction();
            retrySaleCreditCardTransaction.setSecurityCode("123");
            retrySaleCreditCardTransaction.setTransactionKey(UUID.fromString("20ba0520-7d09-44f8-8fbc-e4329e2b18d5"));

            // Cria a lista de transações de cartão e adiciona a transação criada a lista
            List<RetrySaleCreditCardTransaction> manageCreditCardTransactionList = new ArrayList<RetrySaleCreditCardTransaction>();
            manageCreditCardTransactionList.add(retrySaleCreditCardTransaction);

            // Define a chave do pedido
            retrySaleRequest.setOrderKey(UUID.fromString("219d7581-78e2-4aa9-b708-b7c585780bfc"));
            retrySaleRequest.setRetrySaleCreditCardTransactionCollection(manageCreditCardTransactionList);

            // Submete a requisição
            HttpResponseGenerics<RetrySaleResponse, RetrySaleRequest> httpResponse
                    = serviceClient.getSale().Retry(retrySaleRequest);

            return httpResponse.getRawResponse();
        } catch (Exception e) {
            return "Error";
        }
    }
}
