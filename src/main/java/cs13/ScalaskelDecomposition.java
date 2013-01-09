package cs13;

/**
 * User: xavierhanin
 * Date: 1/9/13
 * Time: 4:03 PM
 */
public final class ScalaskelDecomposition {

    public static enum Coin {
        FOO(1), BAR(7), QIX(11), BAZ(21);

        private final int value;
        Coin(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }

    }
    private int foo;
    private int bar;
    private int baz;
    private int qix;

    public int total() {
        return Coin.FOO.getValue() * foo
                + Coin.BAR.getValue() * bar
                + Coin.QIX.getValue() * qix
                + Coin.BAZ.getValue() * baz;
    }

    public ScalaskelDecomposition increment(Coin coin, int i) {
        switch (coin) {
            case FOO: foo += i; break;
            case BAR: bar += i; break;
            case QIX: qix += i; break;
            case BAZ: baz += i; break;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScalaskelDecomposition that = (ScalaskelDecomposition) o;

        if (bar != that.bar) return false;
        if (baz != that.baz) return false;
        if (foo != that.foo) return false;
        if (qix != that.qix) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = foo;
        result = 31 * result + bar;
        result = 31 * result + baz;
        result = 31 * result + qix;
        return result;
    }

    @Override
    public String toString() {
        return "ScalaskelDecomposition(" + total() + "){" +
                "foo=" + foo +
                ", bar=" + bar +
                ", qix=" + qix +
                ", baz=" + baz +
                '}';
    }


    public ScalaskelDecomposition setFoo(int foo) {
        this.foo = foo;
        return this;
    }

    public int getFoo() {
        return this.foo;
    }

    public ScalaskelDecomposition setBar(int bar) {
        this.bar = bar;
        return this;
    }

    public int getBar() {
        return this.bar;
    }

    public ScalaskelDecomposition setBaz(int baz) {
        this.baz = baz;
        return this;
    }

    public int getBaz() {
        return this.baz;
    }

    public ScalaskelDecomposition setQix(int qix) {
        this.qix = qix;
        return this;
    }

    public int getQix() {
        return this.qix;
    }
}
