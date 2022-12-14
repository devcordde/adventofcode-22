import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.BiConsumer

class Day11 {
    static void main(String[] args) {
        def raw = Files.readString(Path.of("input.txt")).split("\r?\n\r?\n")
                .collect { it.readLines() }
        def monkeyBusiness = { List<Monkey> ms ->
            ms.sort { -it.inspectCounter }.take(2)
                    .inject { a, b -> a.inspectCounter * b.inspectCounter }
        }

        List<Monkey> monkeys = configure(raw)
        for (_ in 0..<20) {
            monkeys*.inspectAll(3) { item, target -> monkeys[target].holding.add(item) }
        }
        println monkeyBusiness(monkeys)

        monkeys = configure(raw)
        for (_ in 0..<10000) {
            monkeys*.inspectAll(1) { i, t -> monkeys[t].holding.add(i) }
        }
        println monkeyBusiness(monkeys)
    }

    private static List<Monkey> configure(List<List<String>> raw) {
        def monkeys = raw.collect {
            def items = it[1].split(": ")[1].split(", ").collect { it.toInteger() }
            def operation = compile(it[2].split("= ")[1])
            def test = it[3].split(" ").last().toInteger()
            def ifTrue = it[4].split(" ").last().toInteger()
            def ifFalse = it[5].split(" ").last().toInteger()
            new Monkey(items, test, ifTrue, ifFalse, operation)
        }
        int f = monkeys.collect { it.test }.inject { a, b -> a * b }
        for (m in monkeys) {
            def old = m.operation
            m.operation = { long it -> (old(it) % f) }
        }
        monkeys
    }

    static class Monkey {
        long inspectCounter = 0
        Queue<Long> holding = new ArrayDeque<>()
        int test
        int ifTrue
        int ifFalse
        def operation

        Monkey(List<Long> items, int test, int ifTrue, int ifFalse, Closure<Long> operation) {
            this.holding.addAll(items)
            this.test = test
            this.ifTrue = ifTrue
            this.ifFalse = ifFalse
            this.operation = operation
        }

        void inspectAll(int div, BiConsumer<Long, Integer> throwTo) {
            while (!holding.isEmpty()) {
                inspectCounter++
                def item = operation(holding.removeFirst()).intdiv(div)
                throwTo(item, item % test == 0 ? ifTrue : ifFalse)
            }
        }
    }

    static final MethodType BINARY_FUN = MethodType.methodType(long.class, long.class, long.class)
    static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup()
    static final MethodHandle ADD = LOOKUP.findStatic(Day11.class, "add", BINARY_FUN)
    static final MethodHandle MUL = LOOKUP.findStatic(Day11.class, "mul", BINARY_FUN)
    static long add(long a, long b) { a + b }
    static long mul(long a, long b) { a * b }

    static Closure<Long> compile(String code) {
        def parts = code.trim().split(" ")
        def mh = parts[1] == "+" ? ADD : MUL
        [parts[0], parts[2]].eachWithIndex { String elem, int i ->
            if (elem != "old") {
                mh = MethodHandles.insertArguments(mh, i, elem as long)
            }
        }
        if (mh.type().parameterCount() == 2) {
            mh = MethodHandles.permuteArguments(mh, mh.type().dropParameterTypes(1, 2), 0, 0)
        }
        return { long l -> mh.invokeWithArguments(l) as long }
    }
}
