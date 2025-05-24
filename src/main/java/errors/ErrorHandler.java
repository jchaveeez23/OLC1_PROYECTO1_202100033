package errors;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private static ErrorHandler instance;
    private List<TokenError> errors;

    private ErrorHandler() {
        this.errors = new ArrayList<>();
    }

    public static ErrorHandler getInstance() {
        if (instance == null) {
            instance = new ErrorHandler();
        }
        return instance;
    }

    public void addError(TokenError error) {
        errors.add(error);
    }

    public List<TokenError> getErrors() {
        return errors;
    }

    public void setErrors(List<TokenError> errors) {
        this.errors = errors;
    }

    public void clearErrors() {
        errors.clear();
    }

    public void generateReport(String reportType) {
        String directory = "reports";
        File reportDir = new File(directory);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        String filename = directory + "/Reporte de Errores.html";

        List<TokenError> lexicalErrors = new ArrayList<>();
        List<TokenError> syntacticErrors = new ArrayList<>();

        for (TokenError error : errors) {
            // Se comparan ignorando mayúsculas y sin acentos (ajusta según tus necesidades)
            if (error.getType().equalsIgnoreCase("Lexico")) {
                lexicalErrors.add(error);
            } else if (error.getType().equalsIgnoreCase("Sintáctico")) {
                syntacticErrors.add(error);
            }
        }

        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"es\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<title>Reporte de Errores - " + reportType + "</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            writer.println("h1, h2 { color: #333; }");
            writer.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
            writer.println("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }");
            writer.println("th { background-color: #f2f2f2; }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>Reporte de Errores - " + reportType + "</h1>");

            if (reportType.equalsIgnoreCase("all")) {
                writer.println("<h2>Errores Léxicos</h2>");
                if (lexicalErrors.isEmpty()) {
                    writer.println("<p>Ninguno</p>");
                } else {
                    writer.println("<table>");
                    writer.println("<tr><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th><th>Lexema</th></tr>");
                    for (TokenError error : lexicalErrors) {
                        writer.println("<tr><td>" + error.getType() + "</td><td>" + error.getMessage() + "</td><td>" +
                                error.getLine() + "</td><td>" + error.getColumn() + "</td><td>" + error.getLexeme() + "</td></tr>");
                    }
                    writer.println("</table>");
                }
                writer.println("<h2>Errores Sintácticos</h2>");
                if (syntacticErrors.isEmpty()) {
                    writer.println("<p>Ninguno</p>");
                } else {
                    writer.println("<table>");
                    writer.println("<tr><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th><th>Lexema</th></tr>");
                    for (TokenError error : syntacticErrors) {
                        writer.println("<tr><td>" + error.getType() + "</td><td>" + error.getMessage() + "</td><td>" +
                                error.getLine() + "</td><td>" + error.getColumn() + "</td><td>" + error.getLexeme() + "</td></tr>");
                    }
                    writer.println("</table>");
                }
            }

            writer.println("</body>");
            writer.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateReportAsString(String reportType) {
        StringBuilder sb = new StringBuilder();
        List<TokenError> lexicalErrors = new ArrayList<>();
        List<TokenError> syntacticErrors = new ArrayList<>();

        for (TokenError error : errors) {
            if (error.getType().equalsIgnoreCase("Lexico")) {
                lexicalErrors.add(error);
            } else if (error.getType().equalsIgnoreCase("Sintáctico")) {
                syntacticErrors.add(error);
            }
        }

        if (reportType.equalsIgnoreCase("all")) {
            sb.append("Lexical Errors:\n");
            if (lexicalErrors.isEmpty()) {
                sb.append("None\n");
            } else {
                for (TokenError error : lexicalErrors) {
                    sb.append(String.format("Type: %s, Description: %s, Line: %d, Column: %d%n",
                            error.getType(), error.getMessage(), error.getLine(), error.getColumn()));
                }
            }
            sb.append("\nSyntactic Errors:\n");
            if (syntacticErrors.isEmpty()) {
                sb.append("None\n");
            } else {
                for (TokenError error : syntacticErrors) {
                    sb.append(String.format("Type: %s, Description: %s, Line: %d, Column: %d%n",
                            error.getType(), error.getMessage(), error.getLine(), error.getColumn()));
                }
            }
        } else if (reportType.equalsIgnoreCase("lexical")) {
            sb.append("Lexical Errors:\n");
            if (lexicalErrors.isEmpty()) {
                sb.append("None\n");
            } else {
                for (TokenError error : lexicalErrors) {
                    sb.append(String.format("Type: %s, Description: %s, Line: %d, Column: %d%n",
                            error.getType(), error.getMessage(), error.getLine(), error.getColumn()));
                }
            }
        } else if (reportType.equalsIgnoreCase("syntactic")) {
            sb.append("Syntactic Errors:\n");
            if (syntacticErrors.isEmpty()) {
                sb.append("None\n");
            } else {
                for (TokenError error : syntacticErrors) {
                    sb.append(String.format("Type: %s, Description: %s, Line: %d, Column: %d%n",
                            error.getType(), error.getMessage(), error.getLine(), error.getColumn()));
                }
            }
        } else {
            sb.append("Invalid report type specified.");
        }
        return sb.toString();
    }
}
