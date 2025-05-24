import cup.Parser;
import errors.ErrorHandler;
import errors.TokenError;
import interpret.contexts.ContextMatch;
import interpret.contracts.Expression;
import java_cup.runtime.Symbol;
import jflex.Lexer;
import tokens.Token;
import tokens.TokenHandler;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void generateCompiler() {
        try {
            String[] jflexFile = {
                    "src/main/java/jflex/Lexer.jflex", "-d", "src/main/java/jflex"
            };
            String[] cupFile = {
                    "src/main/java/cup/Parser.cup"
            };

            jflex.Main.generate(jflexFile);

            String[] cupArgs = {
                    "-destdir", "src/main/java/cup",
                    "-parser", "Parser",
                    "-symbols", "sym",
                    cupFile[0]
            };
            java_cup.Main.main(cupArgs);

            System.out.println("¡Compiladores generados exitosamente!");

        } catch (Exception e) {
            System.out.println("Error al generar los compiladores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void executeParser(String input) {
        try {

            TokenHandler tokenHandler = TokenHandler.getInstance();
            tokenHandler.clearTokens();
            ErrorHandler errorHandler = ErrorHandler.getInstance();
            errorHandler.clearErrors();

            Lexer scanner = new Lexer(new StringReader(input));
            Parser parser = new Parser(scanner);
            Symbol parseTree = parser.parse();

            if (!errorHandler.getErrors().isEmpty()) {
                for (TokenError error : errorHandler.getErrors()) {
                    System.out.printf(
                            "Error %s en línea %d, columna %d: %s (Lexema: %s)%n",
                            error.getType(),
                            error.getLine(),
                            error.getColumn(),
                            error.getMessage(),
                            error.getLexeme()
                    );
                }
                errorHandler.generateReport("all");
            }

            if (errorHandler.getErrors().isEmpty() && parseTree.value instanceof Expression) {
                Expression ast = (Expression) parseTree.value;

                ContextMatch globalContext = new ContextMatch(0, null);
                Object result = ast.interpret(globalContext);
                System.out.println(result);
            }

        } catch (Exception e) {
            System.out.println("Error en el análisis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileContent = readFile("src/main/java/files/prueba.cmp");
        if (fileContent != null) {
            System.out.println("\n=== Análisis sintáctico ===");
            executeParser(fileContent);
        }
    }
}