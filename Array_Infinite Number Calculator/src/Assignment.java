import java.util.Scanner;

public class Assignment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("두 수를 입력하세요: ");
            String num1 = scanner.next(); // 6 123456 0
            String num2 = scanner.next();

            String result_add = add(num1, num2);
            String result_subtract = subtract(num1, num2);
            String result_multiply = multiply(num1, num2);

            System.out.println("합: " + result_add);
            System.out.println("차: " + result_subtract);
            System.out.println("곱: " + result_multiply);
        }
    }

    // 덧셈 연산 수행
    public static String add(String num1, String num2) {
        int maxLength = Math.max(num1.length(), num2.length());
        int[] result = new int[maxLength + 1]; // 결과 배열의 길이는 최대 입력 수의 자릿수 + 1입니다.

        int carry = 0;
        for (int i = 0; i < maxLength; i++) {
            int digit1 = i < num1.length() ? num1.charAt(num1.length() - 1 - i) - '0' : 0;
            int digit2 = i < num2.length() ? num2.charAt(num2.length() - 1 - i) - '0' : 0;

            int sum = digit1 + digit2 + carry;
            result[maxLength - i] = sum % 10; // 현재 자릿수의 값 저장
            carry = sum / 10; // 올림 값 계산
        }

        result[0] = carry; // 마지막 올림 값 저장

        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            sb.append(digit);
        }

        // 결과에서 불필요한 앞의 0 제거
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        // 결과가 0일 경우 "0" 반환
        if (sb.length() == 0) {
            sb.append(0);
        }

        return sb.toString();
    }

    // 뺄셈 수행
    public static String subtract(String num1, String num2) {

        // num1이 크거나 같은지 확인
        boolean isNegative = false; // false 로 초기화 한다. 뺄셈 결과가 음수인지 여부를 나타내는 변수

        if (negativeEqual(num2, num1)) { // isGreaterOrEqual
            String temp = num1; // 입력된 숫자 중에서 더 큰 숫자를 `num1`에 할당한다.
            num1 = num2;
            num2 = temp;
            isNegative = true;
        }

        // 뺄셈 수행
        // StringBuilder() 문자열을 처리하기 위한 자바 내장 클래스
        StringBuilder result = new StringBuilder(); // StringBuilder 객체 생성

        int carry = 0; // 뺄셈 연산에서 발생하는 '빌림수'를 의미하는 변수 초기화

        int maxLength = Math.max(num1.length(), num2.length()); // 두 입력 문자열 중에서 더 긴 문자열의 길이를 찾음 => 반복문 시행 횟수

        for (int i = 0; i < maxLength; i++) {
            int subnum1 = i < num1.length() ? num1.charAt(num1.length() - 1 - i) - '0' : 0;
            int subnum2 = i < num2.length() ? num2.charAt(num2.length() - 1 - i) - '0' : 0;

            int sub = subnum1 - subnum2 - carry; // 현재 자릿수에서의 뺄셈 결과 계산

            if (sub < 0) {
                sub += 10; // 뺄셈 결과가 음수라면, 10을 더해주고
                carry = 1; // 빌림수에 +1
            } else { // 그렇지 않다면, '빌림'수 초기화
                carry = 0;
            }

            result.insert(0, sub); // StringBuilder 객체인 `result`의 맨 앞에 `sub`값을 삽입하는 작업 수행
                                   // sub의 값을 인덱스 0 = 문자열 가장 앞쪽에 추가
        }

        // 결과 값의 맨 앞이 0인 경우, 0을 제거
        while (result.length() > 1 && result.charAt(0) == '0') { // 문자열이 한 글자 이상이고, 첫 글자가 `0`일 경우에만 수행
            result.deleteCharAt(0);
        }

        // 결과가 음수일 경우 "-" 기호 추가
        if (isNegative) {
            result.insert(0, '-');
        }

        return result.toString();
    }

    // num1이 num2보다 크거나 같은지 확인
    private static boolean negativeEqual(String num1, String num2) {
        if (num1.length() != num2.length()) {
            return num1.length() > num2.length();
        }
        return num1.compareTo(num2) >= 0;
    }

    // 곱셈 수행
    public static String multiply(String num1, String num2) {

        // 두 수를 곱한 결과의 각 자리수의 곱을 저장하는 배열 => 해당 배열의 크기는 두 입력 수의 자릿수를 더한 값
        int[] products = new int[num1.length() + num2.length()];

        // 뒤에서부터 각 자리수를 곱하고, 올림수를 계산하여 배열에 저장 => 곱셈을 진행할 때 각 자릿수를 더해주기 위해서
        for (int i = num1.length() - 1; i >= 0; i--) { // 각 자릿수의 오른쪽 부터 시작하여 왼쪽으로 이동
            for (int j = num2.length() - 1; j >= 0; j--) { // num1과 마찬가지로 오른쪽부터 왼쪽으로

                int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); // 두 수의 각 자릿수를 곱한 값을 계산, 문자를 숫자로 변환하기 위해
                                                                               // `0`을 뺌

                int sum = product + products[i + j + 1]; // 곱셈 결과를 배열에 더함, 올림수 처리를 위해 이전 값 + 합, 자릿수, 올림수 각각 저장
                products[i + j] += sum / 10;
                products[i + j + 1] = sum % 10;
            }
        }

        // 배열을 문자열로 변환
        StringBuilder result = new StringBuilder();
        for (int digit : products) {
            if (!(result.length() == 0 && digit == 0)) { // 앞의 0은 생략
                result.append(digit);
            }
        }

        return result.length() == 0 ? "0" : result.toString(); // 결과가 `0`이면 `0`을 반환하고, 아니면 문자열 반환
    }

}
