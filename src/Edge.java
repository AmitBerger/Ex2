import api.EdgeData;

public class Edge implements EdgeData {
    private int src = 0;
    private int dst = 0;
    private double wgt = 0;
    private int tag = 0;

    public Edge(int s, int d, double w, int t) {
        this.src = s;
        this.dst = d;
        this.wgt = w;
        this.tag = t;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dst;
    }

    @Override
    public double getWeight() {
        return this.wgt;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch != '.' && ch != ':' && (ch > '9' || ch < '0')) {
                continue;
            }
            temp += s.charAt(i);
            if (temp.equals("src:")) {
                this.src = Integer.parseInt(s.charAt(i + 1) + "");
                temp = "";
            } else if (temp.equals("dest:")) {
                this.dst = Integer.parseInt(s.charAt(i + 1) + "");
                temp = "";
            } else if (temp.equals("weight:")) {
                String d = "";
                boolean flag = true;
                while (ch <= '9' && ch >= '0' || ch == '.' && flag) {
                    if (ch == '.') {
                        flag = false;
                    }
                    d += ch;
                }
                this.wgt = Double.parseDouble(d);
                temp = "";
            } else if (temp.equals("tag")) {
                this.tag = Integer.parseInt(s.charAt(i + 1) + "");
                temp = "";
            }
        }
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
