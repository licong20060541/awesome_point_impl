#include <iostream>
#include <pthread.h>
#include <unistd.h>

using namespace std;

#define NUM_THREADS 5

// 线程的运行函数
void *say_hello(void *args)
{
    int value = *((int *)args);
    sleep(value);
    cout << "Hello Runoob！" << endl;
    sleep(3);
    cout << "end" << value << endl;
    return 0;
    // or
    // pthread_exit(NULL);
}

int main()
{
    // 定义线程的 id 变量，多个变量使用数组
    pthread_t tids[NUM_THREADS];
    int indexes[NUM_THREADS]; // 用数组来保存i的值
    for (int i = 0; i < NUM_THREADS; ++i)
    {
        indexes[i] = i; //先保存i的值
        //参数依次是：创建的线程id，线程参数，调用的函数，传入的函数参数
        int ret = pthread_create(&tids[i], NULL, say_hello, (void *)&(indexes[i]));
        if (ret != 0)
        {
            cout << "pthread_create error: error_code=" << ret << endl;
            exit(-1);
        }
    }
    //等各个线程退出后，进程才结束，否则进程强制结束了，线程可能还没反应过来；
    pthread_exit(NULL); // 会等待spleep结束的
}
