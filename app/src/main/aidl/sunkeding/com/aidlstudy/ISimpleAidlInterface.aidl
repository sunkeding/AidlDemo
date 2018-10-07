// ISimpleAidlInterface.aidl
package sunkeding.com.aidlstudy;

import sunkeding.com.aidlstudy.bean.StudentBean;

interface ISimpleAidlInterface {
    int add(int num1,int num2);
    int addAge(in StudentBean bean);
}
