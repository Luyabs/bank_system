package ui;

import javax.swing.*;
import java.awt.*;

/**
 * 设置jdialog在屏幕中央
 */
public class SetPosition {      //
    public static void setFrameCenter(JDialog jf){
        Toolkit tl = Toolkit.getDefaultToolkit();
        //获取屏幕的长和宽，不确定什么类型，用double类
        //public abstract Dimension getScreenSize()
        Dimension d = tl.getScreenSize();
        double ScreenHeight = d.getHeight();
        double ScreenWidth = d.getWidth();
        //获取窗体的长和宽,这个是int类的
        int FrameWidth = jf.getWidth();
        int FrameHeight = jf.getHeight();
        //相减再除以2，因为设置窗体出现的坐标所需要的是int类的，所以要转换成int类
        int Higth = (int)(ScreenHeight - FrameHeight)/2;
        int Width = (int)(ScreenWidth - FrameWidth)/2;
        //值作为窗体出现的坐标
        jf.setLocation(Width, Higth);
    }
}
