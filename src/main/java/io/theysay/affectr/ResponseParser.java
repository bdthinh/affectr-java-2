package io.theysay.affectr;

import java.util.ArrayList;
import java.util.Arrays;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseParser {
    ResponseParser() {}

    public void outputAdvertisement(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Advertisement Detection ###");

        JSONObject ad = response.getBody().getObject();

        String print = StringUtils.join(new Object[]{
          ad.getJSONObject("score").get("label"),
          ad.getJSONObject("score").get("confidence")
        }, "\t");

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputChunkParse(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Chunk parsing ###");

        JSONArray chunks = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < chunks.length(); i++) {
          print.append(StringUtils.join(new Object[]{
            chunks.getJSONObject(i).getJSONObject("head").get("wordIndex"),
            chunks.getJSONObject(i).getJSONObject("chunk").get("chunkType"),
            chunks.getJSONObject(i).getJSONObject("head").get("text"),
            chunks.getJSONObject(i).getJSONObject("chunk").get("text")
          }, "\t") + "\n");
        }

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputDependencyParse(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Dependency parsing ###");

        JSONArray dependencies = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < dependencies.length(); i++) {
          JSONObject dependency = dependencies.getJSONObject(i).getJSONObject("dependency");
          JSONObject dependent = dependencies.getJSONObject(i).getJSONObject("dependent");
          JSONObject governor = dependencies.getJSONObject(i).optJSONObject("governor");
          String governorLabel;

          if (governor != null) governorLabel = governor.getString("text");
          else governorLabel = "root";

          print.append(StringUtils.join(new Object[]{
            dependent.get("wordIndex"),
            dependency.get("relation") + "(" +
            governorLabel + ", " +
            dependent.get("text") + "-" +
            dependent.get("wordIndex") + ")"
          }, "\t") + "\n");
        }

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputEmotionDocument(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Emotion tagging (document) ###");

        JSONObject emotion = response.getBody().getObject();
        JSONArray emotions = emotion.getJSONArray("emotions");
        String print = StringUtils.join(getEmotionScores(emotions), " ");

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputEmotionSentence(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Emotion tagging (sentences) ###");

        JSONArray sentences = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < sentences.length(); i++) {
          JSONArray emotions = sentences.getJSONObject(i).getJSONArray("emotions");
          ArrayList<String> data = new ArrayList<String>();
          data.add(String.valueOf(sentences.getJSONObject(i).getInt("sentenceIndex")));
          data.addAll(Arrays.asList(getEmotionScores(emotions)));
          data.add(sentences.getJSONObject(i).getString("text"));

          print.append(StringUtils.join(data.toArray(new String[data.size()]), "\t") + "\n");
        }

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputGender(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Gender detection ###");

        JSONObject gender = response.getBody().getObject();

        String print = StringUtils.join(new Object[]{
          gender.getJSONObject("score").get("label"),
          gender.getJSONObject("score").get("confidence")
        }, "\t");

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputHumourDocument(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Humour detection (document) ###");

        JSONObject humour = response.getBody().getObject();

        String print = StringUtils.join(new Object[]{
          humour.getJSONObject("score").get("label"),
          humour.getJSONObject("score").get("confidence")
        }, "\t");

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputHumourSentence(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Humour detection (sentences) ###");

        JSONArray sentences = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < sentences.length(); i++) {
          JSONObject sentence = sentences.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(String.valueOf(sentence.getInt("sentenceIndex")));
          data.add(sentence.getJSONObject("score").getString("label"));
          data.add(String.valueOf(sentence.getJSONObject("score").getDouble("confidence")));
          data.add(sentence.getString("text"));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputIntent(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Intent detection (sentences) ###");

        JSONArray sentences = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < sentences.length(); i++) {
          JSONObject sentence = sentences.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(String.valueOf(sentence.getInt("sentenceIndex")));
          data.add(sentence.getString("intentType"));
          data.add(sentence.getString("text"));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputPosTag(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### POS-tagging ###");

        JSONArray posTags = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < posTags.length(); i++) {
          JSONObject posTag = posTags.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(posTag.getString("posTag"));
          data.add(posTag.getString("posTaggedWord"));
          data.add(String.valueOf(posTag.getInt("sentenceIndex")));
          data.add(posTag.getString("stem"));
          data.add(posTag.getString("text"));
          data.add(String.valueOf(posTag.getInt("wordIndex")));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputRisk(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Risk detection (sentences) ###");

        JSONArray sentences = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < sentences.length(); i++) {
          JSONObject sentence = sentences.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(String.valueOf(sentence.getInt("sentenceIndex")));
          data.add(sentence.getString("riskType"));
          data.add(sentence.getString("text"));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputNamedEntity(HttpResponse<JsonNode> response, String text) {
      try {
          System.out.println("### Named entity recognition ###");

          JSONArray entities = response.getBody().getArray();
          StringBuilder print = new StringBuilder();

          for (int i = 0; i < entities.length(); i++) {
            JSONObject entity = entities.getJSONObject(i);
            ArrayList<String> data = new ArrayList<String>();

            data.add(String.valueOf(entity.getJSONArray("namedEntityTypes")));
            data.add(String.valueOf(entity.getInt("headIndex")));
            data.add(entity.getString("head"));
            data.add(entity.getString("text"));

            print.append(StringUtils.join(data, "\t") + "\n");
          }

          System.out.println(text + "\n" + print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputSentimentDocument(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Sentiment classification (document) ###");

        JSONObject sentiment = response.getBody().getObject();
        StringBuilder print = new StringBuilder();
        ArrayList<String> data = new ArrayList<String>();

        data.add(String.valueOf(sentiment.getJSONObject("sentiment").getString("label")));
        data.add(String.valueOf(sentiment.getJSONObject("sentiment").getDouble("positive")));
        data.add(String.valueOf(sentiment.getJSONObject("sentiment").getDouble("neutral")));
        data.add(String.valueOf(sentiment.getJSONObject("sentiment").getDouble("negative")));

        print.append(StringUtils.join(data, "\t") + "\n");

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputSentimentEntity(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Sentiment classification (entity mentions) ###");

        JSONArray entities = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < entities.length(); i++) {
          JSONObject entity = entities.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(entity.getString("headNoun"));
          data.add(entity.getString("text"));
          data.add(entity.getJSONObject("sentiment").getString("label"));
          data.add(String.valueOf(entity.getJSONObject("sentiment").getDouble("positive")));
          data.add(String.valueOf(entity.getJSONObject("sentiment").getDouble("neutral")));
          data.add(String.valueOf(entity.getJSONObject("sentiment").getDouble("negative")));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(text.replaceAll("\n", " ") + "\n" + print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputSentimentSentence(HttpResponse<JsonNode> response, String text) {
      try {
        System.out.println("### Sentiment classification (sentences) ###");

        JSONArray sentences = response.getBody().getArray();
        StringBuilder print = new StringBuilder();

        for (int i = 0; i < sentences.length(); i++) {
          JSONObject sentence = sentences.getJSONObject(i);
          ArrayList<String> data = new ArrayList<String>();

          data.add(String.valueOf(sentence.getInt("sentenceIndex")));
          data.add(sentence.getJSONObject("sentiment").getString("label"));
          data.add(String.valueOf(sentence.getJSONObject("sentiment").getDouble("positive")));
          data.add(String.valueOf(sentence.getJSONObject("sentiment").getDouble("neutral")));
          data.add(String.valueOf(sentence.getJSONObject("sentiment").getDouble("negative")));
          data.add(sentence.getString("text"));

          print.append(StringUtils.join(data, "\t") + "\n");
        }

        System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    public void outputSpeculation(HttpResponse<JsonNode> response, String text) {
      try {
          System.out.println("### Speculation detection (sentences) ###");

          JSONArray sentences = response.getBody().getArray();
          StringBuilder print = new StringBuilder();

          for (int i = 0; i < sentences.length(); i++) {
            JSONObject sentence = sentences.getJSONObject(i);
            ArrayList<String> data = new ArrayList<String>();

            data.add(String.valueOf(sentence.getInt("sentenceIndex")));
            data.add(sentence.getString("speculationType"));
            data.add(sentence.getString("text"));

            print.append(StringUtils.join(data, "\t") + "\n");
          }

          System.out.println(print + "\n");
      } catch (Exception e) {
        e.printStackTrace();
        printResponseError(response);
      }
    }

    private String[] getEmotionScores(JSONArray emotions) {
      ArrayList<String> scores = new ArrayList<String>();

      for (int i = 0; i < emotions.length(); i++) {
        scores.add(emotions.getJSONObject(i).getString("dimension"));
        scores.add(String.valueOf(emotions.getJSONObject(i).getDouble("score")));
      }

      return scores.toArray(new String[scores.size()]);
    }

    private void printResponseError(HttpResponse<JsonNode> response) {
      System.err.println("Response code: " + response.getCode());
      System.err.println("Unexpected response: " + response.getBody().toString());
    }
}
