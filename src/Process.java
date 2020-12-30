import java.util.Scanner;
import java.util.LinkedList;

//进程控制块
class PCB {
    String name;
    int priority,
            needtime;
    PCB(String name,int priority,int needtime){
        this.name = name;
        this.priority = priority;
        this.needtime = needtime;
    }
}


public class Process {
	private boolean cpu_state = true;
	//true 空闲   ， false 繁忙

	private LinkedList<PCB> readyBox = new LinkedList<>();//就绪队列
	private LinkedList<PCB> blockedBox = new LinkedList<>();//阻塞队列
	private LinkedList<PCB> cpuBox = new LinkedList<>();//cpu

	//创建一个进程
	public boolean creat(int num){
		if(num <= 0) {
			System.out.println("输入错误,请重新选择");
			return false;
			}

		boolean num_state = true;
		while(num_state){
			Scanner input= new Scanner(System.in);
			System.out.println("输入进程名(一个字符串):");
			String name = input.nextLine();
			System.out.println("优先级(一个整数):");
			int priority = Integer.parseInt(input.nextLine());
			System.out.println("所需时间(一个整数):");
			int needtime = Integer.parseInt(input.nextLine());
			readyBox.offer(new PCB(name,priority,needtime));
			num--;
			if(num == 0)
				num_state = false;
		}

		return dispatch();	//执行cpu
	}

	//执行
	public boolean dispatch(){

		if(cpu_state){
			if(!readyBox.isEmpty()){
				PCB ready = readyBox.poll();
				cpuBox.offer(new PCB(ready.name,ready.priority,ready.needtime));
				cpu_state = false;
				System.out.println(cpuBox.peek().name + "进程送往CPU执行");
				cpuBox.peek().priority--;
				cpuBox.peek().needtime--;
				return true;
			}
			else{
				System.out.println("就绪队列为空，无法调度");
			}
		}
		else{
			System.out.println("CPU忙，无法调度");
		}
		return false;
	}


	//时间片完
	public boolean timeout(){
		if(!cpu_state){//cpu繁忙
			if(cpuBox.peek().needtime == 0){
				System.out.println(cpuBox.peek().name + "时间片用完，并且执行完毕，被释放");
			}
			else{
				PCB cpu = cpuBox.poll();
				readyBox.offer(new PCB(cpu.name,cpu.priority,cpu.needtime));
			}
			cpu_state = true;
			if(readyBox.size() != 0){//时间片用完，如果就绪队列不为空，则调度
				return dispatch();
			}
		}
		else{
			System.out.println("没有进程在CPU中，无法超时");
			return false;
		}
		return true;
	}

	//阻塞
    public boolean eventWait(){
	    if(!cpu_state){
            PCB cpu = cpuBox.poll();
	        blockedBox.offer(new PCB(cpu.name,cpu.priority,cpu.needtime));
            System.out.println(cpu.name + "被阻塞");
            cpu_state = true;
            if(readyBox.size() != 0){
                return dispatch();
            }
        }
        else{
            System.out.println("没有进程在CPU中，无法阻塞");
            return false;
        }
	    return true;
    }

    //激活
    public boolean eventOccur(){
	    if(blockedBox.size() != 0){
	        PCB readys = blockedBox.poll();
	        readyBox.offer(new PCB(readys.name,readys.priority,readys.needtime));
            System.out.println(readys.name + "被唤醒");
            if(cpu_state){
               return dispatch();
            }
        }
	    else{
            System.out.println("阻塞队列为空，无法唤醒");
            return false;
        }
	    return true;
    }

    public static void main(String[] args){
	    Process process = new Process();
	    int cpuTime = 1;//cpu运行的时间
        while (true){
            System.out.println("1:New\t\t\t2:Dispatch");
            System.out.println("3:Timeout\t\t4:Event wait");
            System.out.println("5:Event occur\t\t0:exit");
            System.out.println("输入1--5实现相应的功能：");
            int select;
            Scanner input = new Scanner(System.in);
            select = Integer.parseInt(input.nextLine());
            switch(select){
                case 1:
                    int number;
                    System.out.println("输入要创建的进程数：");
                    number = Integer.parseInt(input.nextLine());
                    process.creat(number);
                    break;
                case 2:
                    process.dispatch();
                    break;
                case 3:
                    process.timeout();
                    break;
                case 4:
                    process.eventWait();
                    break;
                case 5:
                    process.eventOccur();
                    break;
                case 0:
                    System.exit(0);
            }
            System.out.println("**************************** cpuTime：" + cpuTime + " ****************************");
            System.out.println("状态\t\t进程名\t\t需要时间\t\t优先级");
            if(!process.cpu_state){
                System.out.print("Running：" + process.cpuBox.peek().name + "\t\t\t");
                System.out.print(process.cpuBox.peek().needtime + "\t\t\t");
                System.out.println(process.cpuBox.peek().priority);
            }
            if(process.readyBox.size() != 0){
                for(int i = 0; i < process.readyBox.size(); i++){
                    System.out.print("Ready：" + process.readyBox.peek().name + "\t\t\t");
                    System.out.print(process.readyBox.peek().needtime + "\t\t\t");
                    System.out.println(process.readyBox.peek().priority);
                }
            }
            if(process.blockedBox.size() != 0){
                for(int i = 0; i < process.blockedBox.size(); i++){
                    System.out.print("Ready：" + process.blockedBox.peek().name + "\t\t\t");
                    System.out.print(process.blockedBox.peek().needtime + "\t\t\t");
                    System.out.println(process.blockedBox.peek().priority);
                }
            }
            System.out.println("*******************************************************************");
            cpuTime++;
        }
    }
}