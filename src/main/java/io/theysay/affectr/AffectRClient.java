package io.theysay.affectr;

import java.util.concurrent.*;

import com.mashape.unirest.http.*;
import org.json.*;

public class AffectRClient {
    private String baseUrl;
    private String userName;
    private String passWord;

    private ResponseParser responseParser = new ResponseParser();

    final String endPointAdvertisement = "/ad";
    final String endPointChunkParse = "/chunkparse";
    final String endPointDependencyParse = "/depparse";
    final String endPointEmotion = "/emotion";
    final String endPointGender = "/gender";
    final String endPointHumour = "/humour";
    final String endPointIntent = "/intent";
    final String endPointNamedEntity = "/namedentity";
    final String endPointPosTag = "/postag";
    final String endPointRisk = "/risk";
    final String endPointSentiment = "/sentiment";
    final String endPointSpeculation = "/speculation";

    AffectRClient(String baseUrl, String userName, String passWord) {
      this.baseUrl = baseUrl;
      this.userName = userName;
      this.passWord = passWord;
    }

    public void demo(Texts demoTexts) {
      try {
        Future<HttpResponse<JsonNode>> advertisement = callAdvertisement(demoTexts.text13);
        Future<HttpResponse<JsonNode>> chunkParse = callChunkParse(demoTexts.text1);
        Future<HttpResponse<JsonNode>> depParse = callDependencyParse(demoTexts.text1);
        Future<HttpResponse<JsonNode>> emotionDocument = callEmotionDocument(demoTexts.text10);
        Future<HttpResponse<JsonNode>> emotionSentence = callEmotionSentence(demoTexts.text10);
        Future<HttpResponse<JsonNode>> gender = callGender(demoTexts.text12);
        Future<HttpResponse<JsonNode>> humourDocument = callHumourDocument(demoTexts.text11);
        Future<HttpResponse<JsonNode>> humourSentence = callHumourSentence(demoTexts.text11);
        Future<HttpResponse<JsonNode>> intent = callIntent(demoTexts.text3);
        Future<HttpResponse<JsonNode>> namedEntity = callNamedEntity(demoTexts.text4);
        Future<HttpResponse<JsonNode>> posTag = callPosTag(demoTexts.text1);
        Future<HttpResponse<JsonNode>> risk = callRisk(demoTexts.text5);
        Future<HttpResponse<JsonNode>> sentimentDocument = callSentimentDocument(demoTexts.text8);
        Future<HttpResponse<JsonNode>> sentimentEntity = callSentimentEntity(demoTexts.text7);
        Future<HttpResponse<JsonNode>> sentimentEntityRelation = callSentimentEntityRelation(demoTexts.text9);
        Future<HttpResponse<JsonNode>> sentimentSentence = callSentimentSentence(demoTexts.text6);
        Future<HttpResponse<JsonNode>> speculation = callSpeculation(demoTexts.text2);

        responseParser.outputAdvertisement(advertisement.get(), demoTexts.text13);
        responseParser.outputChunkParse(chunkParse.get(), demoTexts.text1);
        responseParser.outputDependencyParse(depParse.get(), demoTexts.text1);
        responseParser.outputEmotionDocument(emotionDocument.get(), demoTexts.text10);
        responseParser.outputEmotionSentence(emotionSentence.get(), demoTexts.text10);
        responseParser.outputGender(gender.get(), demoTexts.text12);
        responseParser.outputHumourDocument(humourDocument.get(), demoTexts.text11);
        responseParser.outputHumourSentence(humourSentence.get(), demoTexts.text11);
        responseParser.outputIntent(intent.get(), demoTexts.text3);
        responseParser.outputNamedEntity(namedEntity.get(), demoTexts.text4);
        responseParser.outputPosTag(posTag.get(), demoTexts.text1);
        responseParser.outputRisk(risk.get(), demoTexts.text5);
        responseParser.outputSentimentDocument(sentimentDocument.get(), demoTexts.text8);
        responseParser.outputSentimentEntity(sentimentEntity.get(), demoTexts.text7);
        //responseParser.outputSentimentEntityRelation(sentimentEntityRelation.get(), texts.text9);
        responseParser.outputSentimentSentence(sentimentSentence.get(), demoTexts.text6);
        responseParser.outputSpeculation(speculation.get(), demoTexts.text2);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public Future<HttpResponse<JsonNode>> call(String payLoad, String endPoint) {
      return Unirest
               .post(this.baseUrl + endPoint)
               .basicAuth(this.userName, this.passWord)
               .header("Accept", "application/json")
               .header("Content-Type", "application/json")
               .body(payLoad)
               .asJsonAsync();
    }

    public Future<HttpResponse<JsonNode>> callAdvertisement(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointAdvertisement
      );
    }

    public Future<HttpResponse<JsonNode>> callChunkParse(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointChunkParse
      );
    }

    public Future<HttpResponse<JsonNode>> callDependencyParse(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointDependencyParse
      );
    }

    public Future<HttpResponse<JsonNode>> callEmotionDocument(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointEmotion
      );
    }

    public Future<HttpResponse<JsonNode>> callEmotionSentence(String text) {
      return call(
              new JSONObject().put("text", text).put("level", "sentence").toString(),
              endPointEmotion
      );
    }

    public Future<HttpResponse<JsonNode>> callGender(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointGender
      );
    }

    public Future<HttpResponse<JsonNode>> callHumourDocument(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointHumour
      );
    }

    public Future<HttpResponse<JsonNode>> callHumourSentence(String text) {
      return call(
              new JSONObject().put("text", text).put("level", "sentence").toString(),
              endPointHumour
      );
    }

    public Future<HttpResponse<JsonNode>> callIntent(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointIntent
      );
    }

    public Future<HttpResponse<JsonNode>> callNamedEntity(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointNamedEntity
      );
    }

    public Future<HttpResponse<JsonNode>> callPosTag(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointPosTag
      );
    }

    public Future<HttpResponse<JsonNode>> callRisk(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointRisk
      );
    }

    public Future<HttpResponse<JsonNode>> callSentimentDocument(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointSentiment
      );
    }

    public Future<HttpResponse<JsonNode>> callSentimentEntity(String text) {
      return call(
              new JSONObject().put("text", text).put("level", "entity").toString(),
              endPointSentiment
      );
    }

    public Future<HttpResponse<JsonNode>> callSentimentEntityRelation(String text) {
      return call(
              new JSONObject().put("text", text).put("level", "entityrelation").toString(),
              endPointSentiment
      );
    }

    public Future<HttpResponse<JsonNode>> callSentimentSentence(String text) {
      return call(
              new JSONObject().put("text", text).put("level", "sentence").toString(),
              endPointSentiment
      );
    }

    public Future<HttpResponse<JsonNode>> callSpeculation(String text) {
      return call(
              new JSONObject().put("text", text).toString(),
              endPointSpeculation
      );
    }
}
