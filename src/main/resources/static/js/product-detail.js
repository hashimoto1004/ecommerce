const quantityElement = document.getElementById('quantity');
const selectedQuantityElement = document.getElementById('selectedQuantity');
const stockId = document.getElementById('stockId');

function updateStock() {
  const sizeSelect = document.getElementById('sizeSelect');
  const selectedValue = sizeSelect.value;

  const selectedStock = window.stocks.find(
    (stock) => stock.sizeValue == selectedValue
  );

  const quantity = selectedStock ? selectedStock.quantity : 0;
  const selectedStockId = selectedStock ? selectedStock.id : 0;

  // 在庫数を表示
  quantityElement.textContent = quantity;
  stockId.value = selectedStockId;

  // 購入可能な個数を設定
  updateQuantityOptions(quantity);
}

function updateQuantityOptions(quantity) {
  // プルダウンメニューをクリア
  selectedQuantityElement.innerHTML = '';

  // 在庫数に応じてオプションを生成
  for (let i = 1; i <= quantity; i++) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    selectedQuantityElement.appendChild(option);
  }
}

function calculatePrice() {
  const currentQuantity = parseInt(selectedQuantityElement.value, 10);
  const productPriceElement = document.getElementById('productPrice');
  let productPrice = parseInt(
    document.getElementById('productPrice').getAttribute('data-price'),
    10
  );

  // 料金の計算
  productPriceElement.textContent =
    '¥' + (productPrice * currentQuantity).toLocaleString() + '(include tax)';
}

document.addEventListener('DOMContentLoaded', function () {
  updateStock(); // 初回ロード時に在庫を表示
});
