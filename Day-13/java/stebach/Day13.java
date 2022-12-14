import java.io.*;
import java.util.*;

public class Day13 {
   public static void main(String args[]) {
       final InputStream inputStream = System.in;
       Scanner scanner = new Scanner(inputStream);
       List<List<PacketList>> packetPairs = new ArrayList<>();
       List<PacketList> allPackets = new ArrayList<>();

       while (scanner.hasNextLine()) {
           String leftPacketData = scanner.nextLine();
           if (leftPacketData.length() == 0) {
               continue;
           }
           String rightPacketData = scanner.next();

           PacketList leftPacket = parse(leftPacketData);
           PacketList rightPacket = parse(rightPacketData);

           packetPairs.add(Arrays.asList(
                   leftPacket,
                   rightPacket
           ));
           allPackets.add(leftPacket);
           allPackets.add(rightPacket);
       }

       int sum = 0;
       for (int i=0; i<packetPairs.size(); i++) {
           if (Day13.packetSorter(packetPairs.get(i).get(0), packetPairs.get(i).get(1)) == -1) {
               sum += i+1;
           }
       }

       System.out.println("Solution 1: " + sum);

       PacketList additionalPacket1 = parse("[[2]]");
       PacketList additionalPacket2 = parse("[[6]]");
       allPackets.add(additionalPacket1);
       allPackets.add(additionalPacket2);

       allPackets.sort(Day13::packetSorter);

       System.out.println("Solution 2: " + (
               (allPackets.indexOf(additionalPacket1) + 1) *
               (allPackets.indexOf(additionalPacket2) + 1)
       ));
   }

    private static int packetSorter(PacketValue value1, PacketValue value2) {
       if (value1.getClass() == PacketList.class && value2.getClass() == PacketList.class) {
           for (int i=0; i<Math.min(((PacketList)value1).size(), ((PacketList)value2).size()); i++) {
               int check = packetSorter(((PacketList)value1).get(i), ((PacketList)value2).get(i));
               if (check != 0) {
                   return check;
               }
           }
           if (((PacketList) value1).size() < ((PacketList) value2).size()) {
               return -1;
           }
           if (((PacketList) value1).size() > ((PacketList) value2).size()) {
               return 1;
           }
           return 0;
       } else if (value1.getClass() == PacketList.class) {
           return packetSorter(value1, new PacketList(value2));
       } else if (value2.getClass() == PacketList.class) {
           return packetSorter(new PacketList(value1), value2);
       } else if (value1.value < value2.value) {
           return -1;
       } else if (value1.value > value2.value) {
           return 1;
       }
        return 0;
    }

    private static PacketList parse(String packetData) {
        List<Integer> data = new ArrayList<>();
        data.addAll(packetData.chars().mapToObj(r->(r-'0')).toList());
        data.remove(0);
        return new PacketList(data);
    }

    private static class PacketList extends PacketValue {
        private final List<PacketValue> values = new ArrayList<>();

        public PacketList(List<Integer> data) {
            super(0);
            int currentValue = 0;
            boolean hasValue = false;
            while (data.size() > 0) {
                int next = data.remove(0);
                switch (next) {
                    case 43:
                        hasValue = false;
                        values.add(new PacketList(data));
                        break;
                    case 45:
                        if (hasValue) {
                            values.add(new PacketValue(currentValue));
                        }
                        return;
                    case -4:
                        if (hasValue) {
                            values.add(new PacketValue(currentValue));
                        }
                        hasValue = false;
                        currentValue = 0;
                        break;
                    default:
                        hasValue = true;
                        currentValue = currentValue * 10 + next;
                        break;
                }
            }
        }

        public PacketList(PacketValue value) {
            super(0);
            values.add(value);
        }

        public int size() {
            return values.size();
        }

        public PacketValue get(int i) {
            return values.get(i);
        }
    }

    private static class PacketValue {
        private final int value;

        public PacketValue(int value) {
            this.value = value;
        }
    }
}
