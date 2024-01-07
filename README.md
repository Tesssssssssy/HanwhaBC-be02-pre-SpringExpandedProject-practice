# 결과

- 초기 화면
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/3010a5fc-b129-4743-936c-6efad225254f/Untitled.png)
    
- 회원 가입
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/81b625b4-30a0-49b7-890a-277ad3fa5bef/Untitled.png)
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/31dd782b-8a4e-47ab-9600-2576eb0c56ba/Untitled.png)
    
    이메일 인증을 하지 않았다는 예외 처리를 했기 때문에 /emailconfirm 페이지로
    
    이동하지 않고 예외 처리.
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/b9722608-f764-4b3d-ae68-4cc842a0e621/Untitled.png)
    
    google email 인증
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/d028b547-40aa-4c84-8d57-e24b00c45dd1/Untitled.png)
    
    회원 가입 완료 및 자동 로그인
    + JWT token도 잘 도착한 모습 확인.
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/e188f410-ac53-44cc-8f04-4d4b0a8974e8/Untitled.png)
    
    DB에 저장된 모습 확인. 
    
- 상품 등록
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/1b074c42-c05b-43d2-b2dc-e0eec5e8fb6e/Untitled.png)
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/c2fe98df-4866-469e-a360-6b06a45cfee8/Untitled.png)
    
    로그인 되어 있는 상태이고 token도 있으므로 상품 등록 정상 접속 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/469195a2-2616-463e-9675-6ac77c5a8501/Untitled.png)
    
    상품이 정상적으로 등록된 모습 확인.
    
    사진 2개 모두 정상적으로 업로드. 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/0376d06e-e913-4603-8063-9fb9bba1a7ab/Untitled.png)
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/319e5003-cd2f-4326-acdb-948c8c961735/Untitled.png)
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/2941c33c-51de-4fb0-9cd1-860908b98411/Untitled.png)
    
    메인 페이지에서 백엔드의 /product/list 메소드가 실행되고
    
    등록한 상품이 response에 잘 오고 화면에도 잘 출력되는 모습 확인.
    
    저장된 사진 중 첫 번째 사진이 미리 보기 사진으로 잘 출력된다. 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/e0ca1868-ea88-46de-ab97-0256c95e8eb2/Untitled.png)
    
    AWS S3에 지정한 위치로 잘 저장되는 모습 확인.
    
- 장바구니
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/7cc31cc8-c48e-484c-b7ae-8246b363115e/Untitled.png)
    
    장바구니 추가 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/958a0722-2d96-43e2-8a49-cea29e34a57b/Untitled.png)
    
    장바구니에 잘 추가된 모습 확인.
    
    그리고 이미지가 보이지 않는 건 프론트에서 구현이 되지 않아서 그렇다. 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/2d41cb8d-0d78-45fb-8ca8-a723f45bb53f/Untitled.png)
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ab957db7-73a4-4aef-9b1b-7b5310754608/637d404f-e14b-4eef-bf02-f762540f688e/Untitled.png)
    
    그리고 아직 구현은 되지 않았지만 결제 API까지 잘 실행되는 모습 확인.
