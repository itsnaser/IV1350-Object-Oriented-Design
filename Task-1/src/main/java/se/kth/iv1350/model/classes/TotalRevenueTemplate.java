package se.kth.iv1350.model.classes;

import se.kth.iv1350.model.interfaces.ISaleObserver;

public abstract class TotalRevenueTemplate implements ISaleObserver {
  protected double totalRevenue = 0;

  public void newSaleWasMade(double priceOfTheSaleThatWasJustMade) {
    updateRevenue(priceOfTheSaleThatWasJustMade);
    showTotalIncome();
  }

  @Override
  public void updateRevenue(double priceOfTheSaleThatWasJustMade) {
    this.totalRevenue += priceOfTheSaleThatWasJustMade;
  }

  private void showTotalIncome() {
    try {
      doShowTotalIncome();
    } catch (Exception e) {
      handleErrors(e);
    }
  }

  protected abstract void doShowTotalIncome() throws Exception;

  protected abstract void handleErrors(Exception e);
}
