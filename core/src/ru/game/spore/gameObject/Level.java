package ru.game.spore.gameObject;

    public enum Level {
        ZERO(){
            private float maxHP = 50;
            private int maxExp = 10;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }

        },
        ONE(){
            private float maxHP = 100;
            private int maxExp = 50;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        TWO{
            private float maxHP = 150;
            private int maxExp = 100;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        THRE(){
            private float maxHP = 200;
            private int maxExp = 200;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        FOUR(){
            private float maxHP = 250;
            private int maxExp = 400;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        FIVE(){
            private float maxHP = 300;
            private int maxExp = 800;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        SIX(){
            private float maxHP = 350;
            private int maxExp = 1600;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        SEVEN{
            private float maxHP = 400;
            private int maxExp = 3200;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        EIGHT(){
            private float maxHP = 450;
            private int maxExp = 6400;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        NINE(){
            private float maxHP = 500;
            private int maxExp = 12800;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        },
        TEN(){
            private float maxHP = 600;
            private int maxExp = 25600;
            public int getMaxExperience(){
                return maxExp;
            }
            public float getMaxHP(){
                return maxHP;
            }
        };

        public abstract int getMaxExperience();
        public abstract float getMaxHP();

    }

