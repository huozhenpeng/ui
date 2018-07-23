package com.example.huozhenpeng.interviewui.differentcolorbar;

import java.io.Serializable;
import java.util.List;

/**
 * 新版：我的收入页面 柱状图数据源
 * @author huozhenpeng
 *
 */
public class MyIncomeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * data : {"incomeMonth":[{"halfYearMonthIncome":1589.97,"yearIncomeDate":"2016-01"},{"halfYearMonthIncome":3266.94,"yearIncomeDate":"2015-12"},{"halfYearMonthIncome":6605.38,"yearIncomeDate":"2015-11"},{"halfYearMonthIncome":58367.04,"yearIncomeDate":"2015-10"},{"halfYearMonthIncome":2318.59,"yearIncomeDate":"2015-09"},{"halfYearMonthIncome":4413.36,"yearIncomeDate":"2015-08"}],"incomeOrder":[{"halfYearResult":220000,"yearResultDate":"2016-01"},{"halfYearResult":330000,"yearResultDate":"2015-12"},{"halfYearResult":460000,"yearResultDate":"2015-11"},{"halfYearResult":3150000,"yearResultDate":"2015-10"},{"halfYearResult":10000,"yearResultDate":"2015-09"},{"halfYearResult":80000,"yearResultDate":"2015-08"}]}
     * state : 1
     */

    private DataEntity data;
    private int state;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(DataEntity data) {
        this.data = data;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DataEntity getData() {
        return data;
    }

    public int getState() {
        return state;
    }

    public static class DataEntity {
        /**
         * incomeMonth : [{"halfYearMonthIncome":1589.97,"yearIncomeDate":"2016-01"},{"halfYearMonthIncome":3266.94,"yearIncomeDate":"2015-12"},{"halfYearMonthIncome":6605.38,"yearIncomeDate":"2015-11"},{"halfYearMonthIncome":58367.04,"yearIncomeDate":"2015-10"},{"halfYearMonthIncome":2318.59,"yearIncomeDate":"2015-09"},{"halfYearMonthIncome":4413.36,"yearIncomeDate":"2015-08"}]
         * incomeOrder : [{"halfYearResult":220000,"yearResultDate":"2016-01"},{"halfYearResult":330000,"yearResultDate":"2015-12"},{"halfYearResult":460000,"yearResultDate":"2015-11"},{"halfYearResult":3150000,"yearResultDate":"2015-10"},{"halfYearResult":10000,"yearResultDate":"2015-09"},{"halfYearResult":80000,"yearResultDate":"2015-08"}]
         */

        private List<IncomeMonthEntity> incomeMonth;//原来的月收入(最上面显示的)
        private List<IncomeOrderEntity> incomeOrder;//原来的月业绩
        private List<IncomeRewardEntity> reward;//奖励
        private List<InsuranceCommissionEntity> insuranceCommission;//保险产品
        private List<TeamAwardEntity> teamAward;//团队奖励（弃用）
        private List<ProductCommissionEntity> productCommission;//投资产品
        private List<OverseasCommissionEntity>overseasCommission;//海外产品
        private List<GroupAwardEntity> groupAward;//团队津贴
		private List<FundEntity>fundCommission;
		private List<IncomeOrderEntity> discountOrder;//折标后
		private List<AllowanceEntity> allowanceList;//管理津贴

		public List<AllowanceEntity> getAllowanceList() {
			return allowanceList;
		}

		public void setAllowanceList(List<AllowanceEntity> allowanceList) {
			this.allowanceList = allowanceList;
		}

		public List<IncomeOrderEntity> getDiscountOrder() {
			return discountOrder;
		}

		public void setDiscountOrder(List<IncomeOrderEntity> discountOrder) {
			this.discountOrder = discountOrder;
		}

		public List<FundEntity> getFundCommission() {
			return fundCommission;
		}

		public void setFundCommission(List<FundEntity> fundCommission) {
			this.fundCommission = fundCommission;
		}

		public List<GroupAwardEntity> getGroupAward() {
			return groupAward;
		}

		public void setGroupAward(List<GroupAwardEntity> groupAward) {
			this.groupAward = groupAward;
		}

		public List<OverseasCommissionEntity> getOverseasCommission() {
			return overseasCommission;
		}

		public void setOverseasCommission(
				List<OverseasCommissionEntity> overseasCommission) {
			this.overseasCommission = overseasCommission;
		}

		public List<IncomeRewardEntity> getReward() {
			return reward;
		}

		public void setReward(List<IncomeRewardEntity> reward) {
			this.reward = reward;
		}

		public List<InsuranceCommissionEntity> getInsuranceCommission() {
			return insuranceCommission;
		}

		public void setInsuranceCommission(
				List<InsuranceCommissionEntity> insuranceCommission) {
			this.insuranceCommission = insuranceCommission;
		}

		public List<TeamAwardEntity> getTeamAward() {
			return teamAward;
		}

		public void setTeamAward(List<TeamAwardEntity> teamAward) {
			this.teamAward = teamAward;
		}

		public List<ProductCommissionEntity> getProductCommission() {
			return productCommission;
		}

		public void setProductCommission(List<ProductCommissionEntity> productCommission) {
			this.productCommission = productCommission;
		}

		public List<IncomeRewardEntity> getImcomeReward() {
			return reward;
		}

		public void setImcomeReward(List<IncomeRewardEntity> imcomeReward) {
			this.reward = imcomeReward;
		}

		public void setIncomeMonth(List<IncomeMonthEntity> incomeMonth) {
            this.incomeMonth = incomeMonth;
        }

        public void setIncomeOrder(List<IncomeOrderEntity> incomeOrder) {
            this.incomeOrder = incomeOrder;
        }

        public List<IncomeMonthEntity> getIncomeMonth() {
            return incomeMonth;
        }

        public List<IncomeOrderEntity> getIncomeOrder() {
            return incomeOrder;
        }
		public static class FundEntity{
			private double halfYearMonthIncome;
			private String yearIncomeDate;

			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}

			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}

			public String getYearIncomeDate() {
				return yearIncomeDate;
			}

			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
		}

        public static class IncomeMonthEntity {
            /**
             * halfYearMonthIncome : 1589.97
             * yearIncomeDate : 2016-01
             */

            private double halfYearMonthIncome;
            private String yearIncomeDate;
        	



			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
                this.halfYearMonthIncome = halfYearMonthIncome;
            }

            public void setYearIncomeDate(String yearIncomeDate) {
                this.yearIncomeDate = yearIncomeDate;
            }

            public double getHalfYearMonthIncome() {
                return halfYearMonthIncome;
            }

            public String getYearIncomeDate() {
                return yearIncomeDate;
            }
        }

        public static class IncomeOrderEntity {
            /**
             * halfYearResult : 220000
             * yearResultDate : 2016-01
             */

            private double halfYearResult;
            private String yearResultDate;

            public void setHalfYearResult(double halfYearResult) {
                this.halfYearResult = halfYearResult;
            }

            public void setYearResultDate(String yearResultDate) {
                this.yearResultDate = yearResultDate;
            }

            public double getHalfYearResult() {
                return halfYearResult;
            }

            public String getYearResultDate() {
                return yearResultDate;
            }

			public IncomeOrderEntity(double halfYearResult, String yearResultDate) {
				this.halfYearResult = halfYearResult;
				this.yearResultDate = yearResultDate;
			}
		}
        public static class IncomeRewardEntity
        {
//        	 "halfYearMonthIncome": 0,
//             "yearIncomeDate": "2015-08"
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;

			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	

        }
        
        public static class InsuranceCommissionEntity
        {
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	
        }
        public static class TeamAwardEntity
        {
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	
        }
        public static class ProductCommissionEntity
        {
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	
        }
        
        public static class OverseasCommissionEntity
        {
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	
        }
        
        public static class GroupAwardEntity
        {
        	private double halfYearMonthIncome;
        	private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}
        	
        }

		public static class AllowanceEntity
		{
			private double halfYearMonthIncome;
			private String yearIncomeDate;
			public double getHalfYearMonthIncome() {
				return halfYearMonthIncome;
			}
			public void setHalfYearMonthIncome(double halfYearMonthIncome) {
				this.halfYearMonthIncome = halfYearMonthIncome;
			}
			public String getYearIncomeDate() {
				return yearIncomeDate;
			}
			public void setYearIncomeDate(String yearIncomeDate) {
				this.yearIncomeDate = yearIncomeDate;
			}

		}
    }


}
