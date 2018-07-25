#include <iostream>
using namespace std;

void testThree();

int main()
{
    // int
    cout << "begin int" << endl;
    int *pointer = nullptr;
    pointer = new int;
    *pointer = 5;
    cout << *pointer << endl;
    delete pointer;

    // 数组
    cout << "begin int[]" << endl;
    pointer = new int[5];
    pointer[0] = 9;
    pointer[1] = 8;
    cout << pointer[1] << endl;
    delete[] pointer;

    // 2维数组
    cout << "begin 2维数组" << endl;
    int **p;
    int i, j; //p[4][8]
    //开始分配4行8列的二维数据
    p = new int *[4];
    for (i = 0; i < 4; i++)
    {
        p[i] = new int[8];
    }

    for (i = 0; i < 4; i++)
    {
        for (j = 0; j < 8; j++)
        {
            p[i][j] = j * i;
        }
    }
    //打印数据
    for (i = 0; i < 4; i++)
    {
        for (j = 0; j < 8; j++)
        {
            if (j == 0)
                cout << endl;
            cout << p[i][j] << "\t";
        }
    }
    //开始释放申请的堆
    for (i = 0; i < 4; i++)
    {
        delete[] p[i];
    }
    delete[] p;

    // 3维数组
    cout << "begin 3维数组" << endl;
    testThree();
}

void testThree()
{
    int i, j, k; // p[2][3][4]

    int ***p;
    p = new int **[2];
    for (i = 0; i < 2; i++)
    {
        p[i] = new int *[3];
        for (j = 0; j < 3; j++)
            p[i][j] = new int[4];
    }

    //输出 p[i][j][k] 三维数据
    for (i = 0; i < 2; i++)
    {
        for (j = 0; j < 3; j++)
        {
            for (k = 0; k < 4; k++)
            {
                p[i][j][k] = i + j + k;
                cout << p[i][j][k] << " ";
            }
            cout << endl;
        }
        cout << endl;
    }

    // 释放内存
    for (i = 0; i < 2; i++)
    {
        for (j = 0; j < 3; j++)
        {
            delete[] p[i][j];
        }
    }
    for (i = 0; i < 2; i++)
    {
        delete[] p[i];
    }
    delete[] p;
}