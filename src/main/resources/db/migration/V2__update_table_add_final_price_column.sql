ALTER TABLE "public"."payment" ADD COLUMN final_price FLOAT(2);
ALTER TABLE "public"."payment" DROP COLUMN price;
ALTER TABLE "public"."payment" ADD COLUMN price FLOAT(2);