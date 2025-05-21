import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;

public class TestAPI {

    public static void main(String[] args) {
        try {
            // Imposta la variabile d'ambiente per l'autenticazione
            // System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "key/api-key.json");

            // Frase di esempio da analizzare
            String text = "The dog quickly runs through the big park.";

            // Chiama l'API
            analyzeSyntax(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void analyzeSyntax(String text) throws Exception {
        // Crea il client per l'API
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            // Crea il documento da analizzare
            Document doc = Document.newBuilder()
                    .setContent(text)
                    .setType(Type.PLAIN_TEXT)
                    .build();

            // Richiesta di analisi sintattica
            AnalyzeSyntaxRequest request = AnalyzeSyntaxRequest.newBuilder()
                    .setDocument(doc)
                    .setEncodingType(EncodingType.UTF8)
                    .build();

            // Risposta dell'API
            AnalyzeSyntaxResponse response = language.analyzeSyntax(request);

            // Stampa i risultati
            for (Token token : response.getTokensList()) {
                String word = token.getText().getContent();
                String pos = token.getPartOfSpeech().getTag().name();
                System.out.printf("Word: %-15s POS: %s\n", word, pos);
            }

        } catch (Exception e) {
            System.err.println("Errore nell'accesso all'API: " + e.getMessage());
            throw e;
        }
    }
}
