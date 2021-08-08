package beans.resultbeans

/**

 * 作者：zzx on 2021/8/8 14:41

 *  作用： xxxx
 */
data class CheckPhoneCodeResult(
    val code: Int,
    val `data`: Any,
    val message: String,
    val success: Boolean
)