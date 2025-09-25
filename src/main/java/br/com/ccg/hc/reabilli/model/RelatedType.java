package br.com.ccg.hc.reabilli.model;

public enum RelatedType {
    VIDEO("VIDEO"),
    TEXT("TEXT");

    private final String text;

    RelatedType(String text) {
    this.text = text.toUpperCase();
    }

    public static RelatedType fromText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Texto não pode ser vazio.");
        }

        for (RelatedType type : RelatedType.values()) {
            if (type.text.equals(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado para o texto: " + text);
    }
}
