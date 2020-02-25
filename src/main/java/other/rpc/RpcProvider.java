package other.rpc;

public class RpcProvider{
    public static void main(String[] args) throws Exception {
        CalculateService service =new CalculateServiceImpl();
        RpcFramework.publish(service,8888);
    }
}

interface CalculateService{
    String Calculate(People p);
}

class CalculateServiceImpl implements CalculateService{
    public String Calculate(People people){
        int res=people.getA() + people.getB();
        return "计算结果 "+res;
    }

}