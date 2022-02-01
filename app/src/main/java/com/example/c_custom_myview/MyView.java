package com.example.c_custom_myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
//1-4 08:15s
//*自訂Ｖiew用onTouchEvent,還可以自己寫五秒在觸發等等效果
//C.繼承Ｖiew
//D.發現紅字,因為父類別Ｖiew並沒有無傳參數的建構式,引此子類別要自己寫建構式
//E.Generate-> Constructor 發現父類別建構式真的沒有無傳參數
//F.發現Ｖiew建構式都有一個Contact,所有的Ｖiew不會單獨存在
//G.在安著裡面Ｖiew一定會跟著Activity或Service活著,而Service都是在背景,所以這個View通常用在Activity
//H.所以這些Ｖiew原件會存在都是因為Contacs
//I.第二個參數Ａttributset,用在layout會把一些xml屬性設定上去
//J.我這邊想要在xml設定一些屬性所以用 public MyView(Context context, @Nullable AttributeSet attrs)建構式
//K.設定背景顏色為灰色,發現我在xml的是綠色,但建構式後是依我這邊設定為主
//L.是這用Ｖiew來畫圖,用座標畫一個幾何圖形,影響資料都有可能
//M.竟然我們繼承這個View,我們也會針對這,做overriider的動作,表現出多形的部分
//N.多形的運用就在此,呼叫onDraw方法
//O.多形如果你今天進行overiider,把爸爸的方法改寫,那就是全班否定父類別的方法,那何必改寫,你就寫一個onDrawV2就好,換一個名字,甚至還可以保留爸爸的方法,但這是不對的觀念
//P.OverRiidder最重要的觀念是發揚光大,把原來的基礎點都做完後,再到我這帶去進行改良
//Q.那onDraw對一個Ｖiew來說是自動被呼叫的,就有一個表現層,而這個表現曾是來自Canvas畫布在上面作畫,所以這個方法不是我們自行去呼叫的
//R.證明onDraw不是我們主動呼叫的,設定log觀察點
//S.//因為有setContentView所以他到xml,去執行MyView,然後建構式物件實體話,同時之後呼叫onDraw方法
//T.所以這邊系統會傳回來canvas給我,我只要在這邊去做畫就好
//U.你要drawCircle,當然要有畫筆,自己定義好筆要什麼顏色,要多寬
//Ｖ.在座標100,100畫出半徑80的圓
//Ｗ.畫一條線
//X.這邊結束畫線的事情,

//目的View能被觸碰,並且在這框框內談跳
//

/*
這種寫法是你先做完了我在將boolean值傳給該方法
*     @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("hank","onTouchEvent ok");
        return super.onTouchEvent(event);
    }
* */

/*setOnClickListener ＝>由上到下一次叫onclick,是由super.onTouchEvent(event)傳來,如果註解將不會被呼叫
當super.onTouchEvent(event);被註解時發現,setOnClickListener,並不會被呼叫,原來onTouchEvent是更早被執行由他將onClick事件被呼叫
所以如果註解當super.onTouchEvent(event),setOnClickListener將不會被呼叫,反之如果super的話onclick

*
 */

/*畫線
*  //實作畫線類似簽名效果,首先Down下去拿到第一個Event,接著手沒有拿起來繼續move,直到拿起來會up
   //每點下去會有x,y,把它存起來點跟點之間的x,y都存起來就可以連線,用LinkedList去存放所有的點,ＨashMap存放資料x,y

* */

/*R生命觀察點
 MainActivity -> onCreate super前,Ａ
 MainActivity -> onCreate super後,setContentView前,Ｂ
 這邊多了一段setContentView->,所以才會到xml去呼叫ＭyView的物件實體,去做建構式
 MyView()
MainActivity -> onCreate super後,setContentView後,C
 MyView() -> onDraw() （被呼叫建構式後一邊onDraw）
*
* */
public class MyView extends View {//C.繼承Ｖiew
    //7.每點下去會有x,y,把它存起來點跟點之間的x,y都存起來就可以連線,用LinkedList去存放所有的點,ＨashMap存放資料x,y
    private LinkedList<HashMap<String,Float>> line;
    //9.準備畫筆
    private Paint paint;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //K.設定背景顏色為灰色,發現我在xml的是綠色,但建構式後是依我這邊設定為主
        setBackgroundColor(Color.GRAY);
        Log.v("hank", "MyView() 建構式");

        //5.看看onclick跟onTouchEvent的關西
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("hank","onClick()");
//            }
//        });


        //7.初始化line
        line = new LinkedList<>();

        //9.準備要畫線的筆
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }

    //N.多形的運用就在此,呼叫onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("hank", "MyView() -> onDraw()");
        //T.所以這邊系統會傳回來canvas給我,我只要在這邊去做畫就好
//
//        //U.你要drawCircle,當然要有畫筆,自己定義好筆要什麼顏色,要多寬
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);//設定顏色
//        paint.setStrokeWidth(10);//設定筆的寬度
//
//        //Ｖ.在座標100,100畫出半徑80的圓
//        canvas.drawCircle(100,100,80,paint);
//
//        //Ｗ.畫一條線
//        canvas.drawLine(0,0,400,400,paint);


        //9.現在擁有很多的點組成的line,訣竅是如果4個點是3條線所以如果0開始,會從0畫到1,1畫到2,2畫到3,所以是三條線
        for(int i= 1; i<line.size(); i++){
            HashMap<String,Float> p0 = line.get(i-1);//前一個點的位置
            HashMap<String,Float> p1 = line.get(i);  //現在點的位置
            canvas.drawLine(
                    p0.get("x"),
                    p0.get("y"),
                    p1.get("x"),
                    p1.get("y"),
                    paint);
        }
    }

    //1.這個是件非常重要,最基本的也是他,也可以轉乘onClick,這個事件也是事件發生了才會被觸發
    //false:點下去就呼叫,剩下不理他/true:點下去一直滑都會呼叫,直到放開停止
    //此方法回傳,true/false最重要,而super註解但流程人一樣
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("hank", "onTouchEvent ok");

        //4.取得event的資訊
        float ex = event.getX();
        float ey = event.getY();
        Log.v("hank", "onTouchEvent() ex = " + ex + ", ey = " + ey);

        //6.取得action的各個事件
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.v("hank","ACTION_DOWN");
            //8.儲存第一個點的x,y掛到line上
            setFirstPoint(event);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.v("hank","ACTION_UP");
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            //8.儲存移動點的x,y掛到line上
            setMovePoint(event);
            Log.v("hank","ACTION_MOVE");
        }

        //2.觀察預設控點,發現他預設的回傳值是false,//4註解super其實仍然樣,重點再回傳true/false
//        boolean ret = super.onTouchEvent(event); //onTouchEvent ret:false
//        Log.v("hank", "onTouchEvent ret:" + ret);

        //5.當super.onTouchEvent(event);被註解時發現,setOnClickListener,並不會被呼叫,原來onTouchEvent是更早被執行由他將onClick事件被呼叫
//         super.onTouchEvent(event);


        //3.改為true,就是點下去一直滑都會呼叫,直到放開停止
        return true;
    }

    //8.儲存第一個點的x,y掛到line上
    private void setFirstPoint(MotionEvent event) {
        float ex = event.getX(); float ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y" , ey);
        line.add(point);
    }
    //8.儲存移動點的x,y掛到line上
    private void setMovePoint(MotionEvent event) {
        float ex = event.getX(); float ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y" , ey);
        line.add(point);
        invalidate(); //重新更新繪圖
    }
}
