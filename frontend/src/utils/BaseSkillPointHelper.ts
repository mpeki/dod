export const youngBPs: number = 260;
export const matureBPs: number = 320;
export const oldBPs: number = 440;

export const getMaxBPs = (ageGroup: string): number => {
  return ageGroup === "YOUNG" ? youngBPs : ageGroup === "MATURE" ? matureBPs : oldBPs;
};

export const normalise = (value: number, maxBPs: number): number => {
  return ((value - maxBPs) * 100) / (0 - maxBPs);
};

export const normaliseAsc = (value: number, maxBPs: number): number => {
  return ((Math.abs(value - maxBPs)) * 100) / (maxBPs);
};


export const getColor = (bpLeft : number, value : number) => {
  if(bpLeft < 10) return '#006B3E'; // green
  if (value < 50) return '#ec0000'; // dark red
  if (value < 65) return '#ec2400'; // red
  if (value < 80) return '#ec5300'; // dark orange
  if (value < 95) return '#ec9b00'; // dark yellow (YellowGreen)
  if (value < 100) return '#ecca00'; // yellow
  // You can continue with more shades if needed

};