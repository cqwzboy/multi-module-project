package org.fuqinqin.code.order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServlet extends HttpServlet {

    private IOrderService orderService;

    public OrderServlet(){
        this.orderService = new OrderServiceImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        /*String orderNo = req.getParameter("orderNo");
        Long goodsId = Long.parseLong(req.getParameter("goodsId"));
        String goodsName = req.getParameter("goodsName");
        Long userId = Long.parseLong(req.getParameter("userId"));*/

        Long time = System.currentTimeMillis();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("订单编号："+time.hashCode());
        String orderNo = String.valueOf(time.hashCode());
        Long goodsId = time;
        String goodsName = "goodsName"+String.valueOf(time);
        Long userId = time;

        OrderEntity entity = new OrderEntity();
        entity.setOrderNo(orderNo);
        entity.setGoodsId(goodsId);
        entity.setGoodsName(goodsName);
        entity.setUserId(userId);

        int rownum = orderService.add(entity);
        System.out.println("插入行数："+rownum);

        OutputStream os = resp.getOutputStream();
        os.write("add success".getBytes());
        os.flush();
        os.close();
    }
}
