package dddd;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
* @Author 孙雷
* @Title: TwentyFourbot_Game 
* @Description: 实现24点游戏
* @Date 2018年6月24日 下午4:30:04
 */
public class  TwentyFourPoke_Game extends JFrame{
	
	
	/** 
	* @Fields serialVersionUID :给一个序列化的ID，保证序列化的成功，版本的兼容性
	* @Fields jbsolution:定义按钮信息：寻找解
	* @Fields jbrefresh：定义按钮信息：刷新
	* @Fields jbverify：定义按钮信息：验证
	* @Fields jlmessage：定义提示信息：请输入需要验证的答案
	* @Fields jtsolution、jtexpression：定义文本框
	* @Fields pp：定义图像展示框
	* @Fields card 定义牌
	* @Fields bcard 
	* @Fields sum：求和验证
	* @Fields temp1
	* @Fields temp2
	* @Fields sign：定义符号：加减乘除
	*/
	private static final long serialVersionUID = 1L;

    private JButton jbsolution = new JButton("Find a Solution");
    private JButton jbrefresh = new JButton("Refresh");
    private JButton jbverify = new JButton("Verify");
 
    private JLabel jlmessage = new JLabel("Enter an expression:");
 
    private JTextField jtsolution = new JTextField();
    private JTextField jtexpression = new JTextField();
 
    private ImagePanel pp = new ImagePanel();
 
    private int[] card = new int[4];
    private double[] bcard = new double[4];
 
    private double sum;
    private double[] temp1 = new double[3];
    private double[] temp2 = new double[2];
    private char[] sign = {'+','-','*','/'};
 
    /**
     * <p>Title:TwentyFourbot_Game </p> 
     * <p>Description:JPanel面板容器，将窗口定义出来，
     *				 button按钮事件监听,监听三类事件：求解、刷新、验证解</p>
    */
    public TwentyFourPoke_Game()
    {
        JPanel p1 = new JPanel(new GridLayout(1,3));
        p1.add(jbsolution);
        p1.add(jtsolution);
        p1.add(jbrefresh);
        JPanel p3 = new JPanel(new GridLayout(1,3));
        p3.add(jlmessage);
        p3.add(jtexpression);
        p3.add(jbverify);
        //设定位置
        add(p1,BorderLayout.NORTH);
        add(pp,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        //事件监听
        ButtonListener listener = new ButtonListener();
        jbsolution.addActionListener(listener);
        jbrefresh.addActionListener(listener);
        jbverify.addActionListener(listener);
    }
    /**
     * 
    * @Author 孙雷
    * @Title: ButtonListener 
    * @Description: 定义监听器 
    * @Date 2018年6月25日 下午6:30:51
     */
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == jbsolution)
            {
                for(int i = 0;i < 4;i++)
                {
                    bcard[i] = (double)card[i] % 13;
                    if(card[i] % 13 == 0)
                        bcard[i] = 13;
                }
                search();
            }
            else if(e.getSource() == jbrefresh)
            {
                pp.sshow();
 
            }
            else if(e.getSource() == jbverify)
            {
                String expression = jtexpression.getText();
                int result = evaluateExpression(expression);
                if(result == 24)
                {
                    JOptionPane.showMessageDialog(null,"恭喜你！你答对了！","消息框",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"抱歉！请再次尝试。","消息框",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    /**
     * @Title: calcute 
     * @Author 孙雷
     * @Description: 定义运算规则：加减乘除 
     * @param @param a
     * @param @param b
     * @param @param c
     * @param @return    设定文件 
     * @return double    返回类型 
     * @throws
      */
    public static double calcute(double a,double b,char c)
    {
        if(c == '+')
            return a+b;
        else if(c == '-')
            return  a-b;
        else if(c == '*')
            return a*b;
        else if(c == '/' && b != 0)
            return a/b;
        else
            return -1;
    }
    /**
     * @Title: search 
     * @Author 孙雷
     * @Description: 寻找是否有解
     * @param     设定文件 
     * @return void    返回类型 
     * @throws
      */
    public  void search()
    {
        boolean judge = false;
        for(int i=0;i<4;i++)
        //第一次放置的符号
        {
            for(int j=0;j<4;j++)
            //第二次放置的符号
            {
                for(int k=0;k<4;k++)
                //第三次放置的符号
                {
                    for(int m=0;m<3;m++)
                    //首先计算的两个相邻数字，共有3种情况，相当于括号的作用
                    {
                        if(bcard[m+1]==0 && sign[i]=='/') break;
                        temp1[m]=calcute(bcard[m],bcard[m+1],sign[i]);
                        temp1[(m+1)%3]=bcard[(m+2)%4];
                        temp1[(m+2)%3]=bcard[(m+3)%4];
                        //先确定首先计算的两个数字，计算完成相当于剩下三个数，按顺序储存在temp数组中
                        for(int n=0;n<2;n++)
                        //三个数字选出先计算的两个相邻数字，两种情况，相当于第二个括号
                        {
                            if(temp1[n+1]==0 && sign[j]=='/') break;
                            temp2[n]=calcute(temp1[n],temp1[n+1],sign[j]);
                            temp2[(n+1)%2]=temp1[(n+2)%3];
                            //先确定首先计算的两个数字，计算完成相当于剩下两个数，按顺序储存在temp数组中
                            if(temp2[1]==0 && sign[k]=='/') break;
                            sum=calcute(temp2[0],temp2[1],sign[k]);
                            //计算和
                            if(sum==24)
                            //若和为24
                            {
                                judge=true;
                                //判断符为1，表示已求得解
                                    if(m==0 && n==0)
                                    {
                                        String sss ="(("+(int)bcard[0]+sign[i]+(int)bcard[1]+")"+sign[j]+(int)bcard[2]+")"+sign[k]+(int)bcard[3]+"="+(int)sum;
                                        jtsolution.setText(sss);
                                        return ;
                                    }
                                    else if(m==0 && n==1)
                                    {
                                        String sss ="("+(int)bcard[0]+sign[i]+(int)bcard[1]+")"+sign[k]+"("+(int)bcard[2]+sign[j]+(int)bcard[3]+")="+(int)sum;
                                        jtsolution.setText(sss);
                                        return ;
                                    }
                                    else if(m==1 && n==0)
                                    {
                                        String sss ="("+(int)bcard[0]+sign[j]+"("+(int)bcard[1]+sign[i]+(int)bcard[2]+"))"+sign[k]+(int)bcard[3]+"="+(int)sum;
                                        jtsolution.setText(sss);
                                        return ;
                                    }
                                    else if(m==2 && n==0)
                                    {
                                        String sss ="("+(int)bcard[0]+sign[j]+(int)bcard[1]+")"+sign[k]+"("+(int)bcard[2]+sign[i]+(int)bcard[3]+")="+(int)sum;
                                        jtsolution.setText(sss);
                                        return ;
                                    }
                                    else if(m==2 && n==0)
                                    {
                                        String sss =(int)bcard[0]+sign[k]+"("+(int)bcard[1]+sign[j]+"("+(int)bcard[2]+sign[i]+(int)bcard[3]+"))="+(int)sum;
                                        jtsolution.setText(sss);
                                        return ;
                                    }
                                        //m=0,1,2 n=0,1表示六种括号放置可能，并按照这六种可能输出相应的格式的计算式
                                 
                            }
                        }
                    }
                }
            }
        }
        if(judge==false)
            jtsolution.setText("No solution!");
        //如果没有找到结果，符号位为0
    }
 
    /**
     * @Title: evaluateExpression 
     * @Author 孙雷
     * @Description: 判断表达式计算顺序，通过栈来实现对表达式的拆解，将运算符与操作数分别压入两个栈operandStack、operatorStack
     *               创建operandStack来存储操作数、创建operatorStack来存储操作员、提取操作数和操作符
     * @param @param expression
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
      */
    public static int evaluateExpression(String expression)
    {
        // Create operandStack to store operands
        java.util.Stack<Integer> operandStack = new java.util.Stack<Integer>();
 
        // Create operatorStack to store operators
        java.util.Stack<Character> operatorStack = new java.util.Stack<Character>();
 
        // Extract operands and operators
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(expression, "()+-/*", true);
 
        // Phase 1: Scan tokens
        while (tokens.hasMoreTokens())
        {
            String token = tokens.nextToken().trim(); // Extract a token
            if (token.length() == 0) // Blank space
                continue; // Back to the while loop to extract the next token
            else if (token.charAt(0) == '+' || token.charAt(0) == '-')
            {
                // Process all +, -, *, / in the top of the operator stack
                while (!operatorStack.isEmpty() &&(operatorStack.peek().equals('+') ||operatorStack.peek().equals('-') || operatorStack.peek().equals('*') ||
           operatorStack.peek().equals('/')))
                {
                    processAnOperator(operandStack, operatorStack);
                }
                // Push the + or - operator into the operator stack
                operatorStack.push(new Character(token.charAt(0)));
            }
            else if (token.charAt(0) == '*' || token.charAt(0) == '/')
            {
                // Process all *, / in the top of the operator stack
                while (!operatorStack.isEmpty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/')))
                {
                    processAnOperator(operandStack, operatorStack);
                }
 
                // Push the * or / operator into the operator stack
                operatorStack.push(new Character(token.charAt(0)));
            }
            else if (token.trim().charAt(0) == '(')
            {
                operatorStack.push(new Character('(')); // Push '(' to stack
            }
            else if (token.trim().charAt(0) == ')')
            {
                // Process all the operators in the stack until seeing '('
                while (!operatorStack.peek().equals('('))
                {
                     processAnOperator(operandStack, operatorStack);
                }
                operatorStack.pop(); // Pop the '(' symbol from the stack
            }
            else
            {
                // An operand scanned
                // Push an operand to the stack
                operandStack.push(new Integer(token));
            }
        }
 
        // Phase 2: process all the remaining operators in the stack
        while (!operatorStack.isEmpty())
        {
            processAnOperator(operandStack, operatorStack);
        }
 
        // Return the result
        return ((Integer)(operandStack.pop())).intValue();
    }
    /**
     * 
    * @Title: processAnOperator 
    * @Author 孙雷
    * @Description:通过栈，对单一运算式进行求解，将结果也压入栈operandStack
    * @param @param operandStack：储存操作数
    * @param @param operatorStack：储存操作员    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public static void processAnOperator(java.util.Stack<Integer> operandStack,java.util.Stack<Character> operatorStack)
    {
        if (operatorStack.peek().equals('+'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 + op1));
        }
        else if (operatorStack.peek().equals('-'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 - op1));
        }
        else if (operatorStack.peek().equals('*'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 * op1));
        }
        else if (operatorStack.peek().equals('/'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 / op1));
        }
    }
    /**
     * 
    * @Author 孙雷
    * @Title: ImagePanel 
    * @Description: 随机产生图片
    * @Date 2018年6月25日 下午6:32:31
     */
    class ImagePanel extends JPanel{
    	/**
    	 * 
    	* @Title: sshow 
    	* @Author Kyrie Irving
    	* @Description: 随机产生图片编号
    	* @param     设定文件 
    	* @return void    返回类型 
    	* @throws
    	 */
        public void sshow()
        {
            int i;
            for(i = 0;i < 4;i++)
            {
                card[i] = (int)(1 + Math.random() * 52);
            }
            repaint();
        }
        /**
         *获取图片，并展示
         */
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int i;
            int w = getWidth() / 4;
            int h = getHeight();
            int x = 0;
            int y = 0;
            for(i = 0;i < 4;i++)
            {
                ImageIcon imageIcon = new ImageIcon("image/card/" + card[i] + ".jpg");
                Image image = imageIcon.getImage();
                if(image != null)
                {
                    g.drawImage(image,x,y,w,h,this);
                }
                x += w;
            }
        }
    }
    /**
     * 
    * @Title: main 
    * @Author 孙雷
    * @Description: 主方法
    * @param @param args    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public static void main(String[] args)
    {
        TwentyFourPoke_Game frame = new TwentyFourPoke_Game();
        frame.setTitle("24 Poke Game");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(368,200);
        frame.setVisible(true);
    }
}