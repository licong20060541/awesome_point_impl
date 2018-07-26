#include <iostream>
#include <thread>

// g++ -std=c++11 test.cpp -lpthread

// g++ -std=c++11 thread/c11thread.cpp -lpthread
// ./a.out
// 4Hello Concurrent World

// This is not the main thread.
// native_handle 0x700005e46000
// Spawning 5 threads...
// Hello Concurrent World
// This is not the main thread.
// Done spawning threads. Now waiting for them to join:
// pause of 1 seconds ended
// pause of 2 seconds ended
// pause of 3 seconds ended
// pause of 4 seconds ended
// pause of 5 seconds ended
// All threads joined!

// 注释a.detach();后，发现最后的输出有错误信息，因此加上
// 4Hello Concurrent World

// This is not the main thread.
// native_handle 0x700003c07000
// Spawning 5 threads...
// Hello Concurrent World
// This is not the main thread.
// Done spawning threads. Now waiting for them to join:
// pause of 1 seconds ended
// pause of 2 seconds ended
// pause of 3 seconds ended
// pause of 4 seconds ended
// pause of 5 seconds ended
// All threads joined!
// libc++abi.dylib: terminating
// Abort trap: 6


std::thread::id main_thread_id = std::this_thread::get_id();

void hello()
{
    std::cout << "Hello Concurrent World\n";
    if (main_thread_id == std::this_thread::get_id())
        std::cout << "This is the main thread.\n";
    else
        std::cout << "This is not the main thread.\n";
}

void pause_thread(int n)
{
    std::this_thread::sleep_for(std::chrono::seconds(n));
    std::cout << "pause of " << n << " seconds ended\n";
}

int main()
{
    std::thread t(hello); // 创建新线程并执行
    std::cout << t.hardware_concurrency() << std::endl;              //可以并发执行多少个(不准确)
    std::cout << "native_handle " << t.native_handle() << std::endl; //可以并发执行多少个(不准确)
    t.join();

    std::thread a(hello);
    a.detach();
    std::thread threads[5]; // 默认构造线程

    std::cout << "Spawning 5 threads...\n";
    for (int i = 0; i < 5; ++i)
        threads[i] = std::thread(pause_thread, i + 1); // move-assign threads
    std::cout << "Done spawning threads. Now waiting for them to join:\n";
    for (auto &thread : threads)
        thread.join();
    std::cout << "All threads joined!\n";
}