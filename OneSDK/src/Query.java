
import Client.GatewayServiceClient;
import DataContracts.Sale.QuerySaleResponse;
import Utility.HttpResponseGenericResponse;
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
public class Query {

    public static String getResponse() {
        try {
            // Define loja 
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria o cliente que vai efetuar a operação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Define a chave do pedido
            UUID orderKey = UUID.fromString("219d7581-78e2-4aa9-b708-b7c585780bfc"); // Chave do pedido

            // Submete a requisição
            HttpResponseGenericResponse<QuerySaleResponse> httpResponse
                    = serviceClient.getSale().QueryOrder(orderKey);

            return httpResponse.getRawResponse();
        } catch (Exception e) {
            return "Error";
        }
    }
}
