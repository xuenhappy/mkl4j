/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_bamboo_mkl4j_MKL */

#ifndef _Included_org_bamboo_mkl4j_MKL
#define _Included_org_bamboo_mkl4j_MKL
#ifdef __cplusplus
extern "C" {
#endif
#undef org_bamboo_mkl4j_MKL_MKL_WAIT_POLICY_PASSIVE
#define org_bamboo_mkl4j_MKL_MKL_WAIT_POLICY_PASSIVE 3L
#undef org_bamboo_mkl4j_MKL_MKL_WAIT_POLICY_ACTIVE
#define org_bamboo_mkl4j_MKL_MKL_WAIT_POLICY_ACTIVE 2L
/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    setNumThreads
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_setNumThreads
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    disableFastMM
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_bamboo_mkl4j_MKL_disableFastMM
  (JNIEnv *, jclass);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    setBlockTime
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_setBlockTime
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    waitPolicy
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_waitPolicy
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsAdd
 * Signature: (I[FI[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsAdd
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdAdd
 * Signature: (I[DI[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdAdd
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsSub
 * Signature: (I[FI[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsSub
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdSub
 * Signature: (I[DI[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdSub
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsMul
 * Signature: (I[FI[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsMul
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdMul
 * Signature: (I[DI[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdMul
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsDiv
 * Signature: (I[FI[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsDiv
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdDiv
 * Signature: (I[DI[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdDiv
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsPowx
 * Signature: (I[FIF[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsPowx
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloat, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdPowx
 * Signature: (I[DID[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdPowx
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdouble, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsLn
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsLn
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdLn
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdLn
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsExp
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsExp
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdExp
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdExp
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsSqrt
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsSqrt
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdSqrt
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdSqrt
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdTanh
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdTanh
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsTanh
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsTanh
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsLog1p
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsLog1p
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdLog1p
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdLog1p
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsAbs
 * Signature: (I[FI[FI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsAbs
  (JNIEnv *, jclass, jint, jfloatArray, jint, jfloatArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdAbs
 * Signature: (I[DI[DI)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdAbs
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jdoubleArray, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsgemm
 * Signature: (CCIIIF[FII[FIIF[FII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsgemm
  (JNIEnv *, jclass, jchar, jchar, jint, jint, jint, jfloat, jfloatArray, jint, jint, jfloatArray, jint, jint, jfloat, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdgemm
 * Signature: (CCIIID[DII[DIID[DII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdgemm
  (JNIEnv *, jclass, jchar, jchar, jint, jint, jint, jdouble, jdoubleArray, jint, jint, jdoubleArray, jint, jint, jdouble, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsgemv
 * Signature: (CIIF[FII[FIIF[FII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsgemv
  (JNIEnv *, jclass, jchar, jint, jint, jfloat, jfloatArray, jint, jint, jfloatArray, jint, jint, jfloat, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdgemv
 * Signature: (CIID[DII[DIID[DII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdgemv
  (JNIEnv *, jclass, jchar, jint, jint, jdouble, jdoubleArray, jint, jint, jdoubleArray, jint, jint, jdouble, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsaxpy
 * Signature: (IF[FII[FII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsaxpy
  (JNIEnv *, jclass, jint, jfloat, jfloatArray, jint, jint, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdaxpy
 * Signature: (ID[DII[DII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdaxpy
  (JNIEnv *, jclass, jint, jdouble, jdoubleArray, jint, jint, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsdot
 * Signature: (I[FII[FII)F
 */
JNIEXPORT jfloat JNICALL Java_org_bamboo_mkl4j_MKL_vsdot
  (JNIEnv *, jclass, jint, jfloatArray, jint, jint, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vddot
 * Signature: (I[DII[DII)D
 */
JNIEXPORT jdouble JNICALL Java_org_bamboo_mkl4j_MKL_vddot
  (JNIEnv *, jclass, jint, jdoubleArray, jint, jint, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsger
 * Signature: (IIF[FII[FII[FII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsger
  (JNIEnv *, jclass, jint, jint, jfloat, jfloatArray, jint, jint, jfloatArray, jint, jint, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdger
 * Signature: (IID[DII[DII[DII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdger
  (JNIEnv *, jclass, jint, jint, jdouble, jdoubleArray, jint, jint, jdoubleArray, jint, jint, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vsscal
 * Signature: (IF[FII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vsscal
  (JNIEnv *, jclass, jint, jfloat, jfloatArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    vdscal
 * Signature: (ID[DII)V
 */
JNIEXPORT void JNICALL Java_org_bamboo_mkl4j_MKL_vdscal
  (JNIEnv *, jclass, jint, jdouble, jdoubleArray, jint, jint);

/*
 * Class:     org_bamboo_mkl4j_MKL
 * Method:    getNumThreads
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_bamboo_mkl4j_MKL_getNumThreads
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
