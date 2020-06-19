package algorithm.leetcode;

public class Question160 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
       int countA = 0;
       int countB = 0;
       while(headA != null) {
           countA++;
           headA = headA.next;
       }
       while(headB != null) {
           countB++;
           headB = headB.next;
       }
       if (countA > countB) {
           for (int i = 0; i < countA - countB; i++) {
               headA = headA.next;
           }
           for (int i = 0; i < countB; i++) {
               if (headA == headB) {
                   return headA;
               }
               headA = headA.next;
               headB = headB.next;
           }
       } else {
           for (int i = 0; i < countB - countA; i++) {
               headB = headB.next;
           }
           for (int i = 0; i < countA; i++) {
               if (headA == headB) {
                   return headA;
               }
               headA = headA.next;
               headB = headB.next;
           }
       }

       return null;
    }
}
