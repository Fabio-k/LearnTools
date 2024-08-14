import { render, screen } from "@testing-library/react";
import ResumeCard from "../../components/ResumeCard";
import { expect } from "vitest";

describe("ResumeArea", () => {
  it("should display title and sumary", () => {
    const resumeData = { title: "a title", id: 1 };
    const resumeSummary = "this is a test";
    render(
      <ResumeCard resumeData={resumeData} resumeSummary={resumeSummary} />
    );
    const header = screen.getByRole("heading");
    expect(header).toHaveTextContent(/title/i);
    expect(screen.getByRole("listitem").className).not.toMatch(
      /resumeSelected/
    );
  });

  it("should have class resumeSelected", () => {
    const resumeData = { title: "a title", id: 1 };
    const selectedResumeId = 1;
    render(
      <ResumeCard resumeData={resumeData} selectedResumeId={selectedResumeId} />
    );
    const li = screen.getByRole("listitem");
    expect(li.className).toMatch(/resumeSelected/);
  });
});
