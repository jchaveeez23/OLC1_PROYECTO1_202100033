package errors;

public class TokenError {
    private String type;
    private int line;
    private int column;
    private String lexeme;
    private String message;
    private String description;
    private String file;

    public TokenError(String type, int line, int column, String lexeme, String message, String description, String file) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
        this.message = message;
        this.description = description;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}