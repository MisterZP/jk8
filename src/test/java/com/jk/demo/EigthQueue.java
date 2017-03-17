package com.jk.demo;

/**
 * Created by zengping on 2017/2/20.
 */
public class EigthQueue {
    /**
     * 1.不在同一行上
     * 2.不在同一列上
     * 3.不在同一斜线上
     * 4.不在同一反斜线上
     *
     *思路：
     * 这为我们提供一种遍历的思路，我们可以逐行或者逐列来进行可行摆放方案的遍历，
     * 每一行（或列）遍历出一个符合条件的位置，接着就到下一行或列遍历下一个棋子的合适位置，
     * 这种遍历思路可以保证我们遍历过程中有一个条件是绝对符合的——就是下一个棋子的摆放位置与前面的棋子不在同一行（或列）。
     * 接下来，我们只要判断当前位置是否还符合其他条件，如果符合，就遍历下一行（或列）所有位置，看看是否继续有符合条件的位置，
     * 以此类推，如果某一个行（或列）的所有位置都不合适，就返回上一行（或列）继续该行（或列）的其他位置遍历，当我们顺利遍历到最后一行（或列），
     * 且有符合条件的位置时，就是一个可行的8皇后摆放方案，累加一次八皇后可行方案的个数，然后继续遍历该行其他位置是否有合适的，
     * 如果没有，则返回上一行，遍历该行其他位置，依此下去。这样一个过程下来，我们就可以得出所有符合条件的8皇后摆放方案了。
     * 这是一个深度优先遍历的过程，同时也是经典的递归思路
     *
     *
     *假设前面1列的棋子放在第3行，那当前列不能放的位置就一定是3行，2行，4行。
     *因为如果放在这三行上就分别跟前一列的棋子同在一行、同在斜线、同在反斜线上，
     *不符合我们的要求。现在我们用cols数组来表示8个列棋子所放的行数，数组下标从0开始，
     *其中数组下标表示列数，数组的元素值表示该列棋子所在行数，当前列为N（N>=0,N<8），即cols[N-1]=3,则有：
     *
     *    cols[N] != cols[N-1]（=3，表示不在同一行）
     *    cols[N] != cols[N-1]-1（=3-1=2，表示不在同一斜线上)
     *    cols[N]!=cols[N-1]+1(=3+1,表示不在同一反斜线上)
     *
     *这里我们注意到，如果N-2列存在的话，那么我们还要考虑当前列N不与N-2列的棋子同行，
     *同斜线，同反斜线。把当前列N的前面的某一列设为m,则m的所有取值为｛m>=0,m<N｝的集合,故又可在上面式子的基础，归纳为如下：
     *
     *     cols[N] != cols[m]（与第m列的棋子不在同一行）
     *     cols[N] != cols[m] -（N-m）(>=0 ,与第m列的棋子不在同一斜线上)
     *     cols[N] != cols[m] + (N-m)  (<=8-1,与第m列的棋子不在同一反斜线上)
     *
     *具体到代码，很显然，取m的所有值只需要一句循环，同时我们为每一列定义一个长度为8的布尔数组row[],
     * 下标同样是从0开始，我们规定当row[i]=true时，表示该列第i行不能放棋子。这样我们就能写成下列程序段了：
     *
     *       boolean[] rows = new boolean[8];
     *       for(int m=0;m<N;i++){
     *          rows[cols[m]]=true;//当前列N的棋子不能放在前面列m的棋子所在行。
     *          int d = N-m;
     *          //该句用于设置当前列N的棋子不能放在前面列m的棋子的斜线上
     *          if(cols[m]-d >= 0)rows[cols[m]-d]=true；
     *          //该句用于设置当前列N的棋子不能放在前面列m的棋子的反斜线上
     *          if(cols[m]+d <=8-1)rows[cols[m]+d]=true;
     *       }
     */


    public static int num = 0; //累计方案总数
    public static final int MAXQUEEN = 8;//皇后个数，同时也是棋盘行列总数
    public static int[] cols = new int[MAXQUEEN]; //定义cols数组，表示8列棋子摆放情况

    public EigthQueue() {
        //核心函数
        calculation(0);
        System.out.print("\n");
        System.out.println(MAXQUEEN + "皇后问题有" + num + "种摆放方法");
    }

    public void calculation(int n) {
        //遍历该列所有不合法的行，并用rows数组记录，不合法即rows[i]=true
        boolean[] rows = new boolean[MAXQUEEN];
        for (int i = 0; i < n; i++) {
            rows[cols[i]] = true;
            int d = n - i;
            if (cols[i] - d >= 0) rows[cols[i] - d] = true;
            if (cols[i] + d <= MAXQUEEN - 1) rows[cols[i] + d] = true;
        }
        for (int i = 0; i < MAXQUEEN; i++) {
            //判断该行是否合法
            if (rows[i]) continue;
            //设置当前列合法棋子所在行数
            cols[n] = i;
            //当前列不为最后一列时
            if (n < MAXQUEEN - 1) {
                calculation(n + 1);
            } else {
                //累计方案个数
                num++;
                //打印棋盘信息
                printChessBoard();
            }
        }
    }

    public void printChessBoard() {
        System.out.print("第" + num + "种走法： \n");
        for (int i = 0; i < MAXQUEEN; i++) {
            for (int j = 0; j < MAXQUEEN; j++)
                System.out.print(i == cols[j] ? " 0 " : " + ");
            System.out.print("\n");
        }
    }

    @Override
    public String toString() {
        return "求解结束";
    }

    public static void main(String args[]) {
        System.out.println(new EigthQueue());
    }
}
