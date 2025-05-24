package tokens;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TokenHandler {

    private static TokenHandler instance;
    private List<Token> tokens;

    private TokenHandler() {
        this.tokens = new ArrayList<>();
    }

    public static TokenHandler getInstance() {
        if (instance == null) {
            instance = new TokenHandler();
        }
        return instance;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void clearTokens() {
        tokens.clear();
    }

    public void generateReport() {
        String directory = "reports";
        File reportDir = new File(directory);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        String filename = directory + "/Reporte de Tokens.html";

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Token Report</title>");
        html.append("<meta charset=\"UTF-8\">");

        html.append("<style>");
        html.append("table { border-collapse: collapse; width: 100%; }");
        html.append("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
        html.append("th { background-color: #f2f2f2; }");
        html.append("</style></head><body>");
        html.append("<h2>Token Report</h2>");
        html.append("<table>");
        html.append("<tr><th>#</th><th>Token</th><th>Lexeme</th><th>Line</th><th>Column</th></tr>");
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            html.append(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%d</td><td>%d</td></tr>",
                    i + 1, token.getType(), token.getValue(), token.getLine(), token.getColumn()));
        }
        html.append("</table></body></html>");
        try {
            Files.write(Paths.get(filename), html.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
