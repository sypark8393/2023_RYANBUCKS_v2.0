// 수량 감소
function decreaseQuantity() {
	var newQuantity = Number($("#quantity").val()) - 1;
	setQuantityAndTotalPrice(newQuantity);
}

// 수량 증가
function increaseQuantity() {
	var newQuantity = Number($("#quantity").val()) + 1;
	setQuantityAndTotalPrice(newQuantity);
}

// 옵션 변경
function changeOption() {
	setQuantityAndTotalPrice(1);
}

function setQuantityAndTotalPrice(newQuantity) {
	var selectOption = $("#option_no");
    var selectedOption = selectOption.find(":selected");
    var optionStock = Number(selectedOption.data('option-stock'));
    var optionPrice = Number(selectedOption.data('option-price'));
	var salesPrice = Number($(".sales_price dd span.price").text().replace(',', ''));
	
	// 옵션 정보가 없는 메뉴이면
    if(isNaN(optionStock)) {
    	optionStock = Number($("#quantity").attr("max"));
    	optionPrice = 0;
    }
	
	$("#quantity").attr("value", newQuantity);
	
	// 수량 감소 버튼
	if(newQuantity == 1) {	// 변경된 수량이 1이면 수량 감소 버튼 비활성화
		$("#decrease").prop("disabled", true);
	
	} else {
		$("#decrease").prop("disabled", false);
		
	}
	
    // 수량 증가 버튼
    if (newQuantity >= optionStock) { // 변경된 수량이 선택된 옵션의 재고와 같거나 커지면 비활성화
        $(".quantity #increase").prop('disabled', true);
    	
    } else {
    	 $(".quantity #increase").prop('disabled', false);
    	 
    }
    
    // 총 주문 수량
    $(".total_price dd span.info span").html(newQuantity);
    
    // 총 상품 금액
    $(".total_price dd span.price").html(((salesPrice + optionPrice) * newQuantity).toLocaleString('ko-KR'));
}