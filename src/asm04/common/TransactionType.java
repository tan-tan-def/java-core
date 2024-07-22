package asm04.common;

public enum TransactionType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW"),
    TRANSFER("TRANSFER");

    private final String type;

    private TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
